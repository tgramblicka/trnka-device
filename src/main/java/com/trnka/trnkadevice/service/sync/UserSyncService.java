package com.trnka.trnkadevice.service.sync;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.trnka.trnkadevice.domain.UserSequenceKey;
import com.trnka.trnkadevice.repository.SequenceStatisticRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trnka.restapi.dto.StudentDTO;
import com.trnka.trnkadevice.domain.Sequence;
import com.trnka.trnkadevice.domain.User;
import com.trnka.trnkadevice.domain.UserPassedMethodicalSequence;
import com.trnka.trnkadevice.domain.UserSequence;
import com.trnka.trnkadevice.repository.SequenceRepository;
import com.trnka.trnkadevice.repository.UserPassedMethodicsRepository;
import com.trnka.trnkadevice.repository.UserRepository;
import com.trnka.trnkadevice.repository.UserSequenceRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSyncService {

    private final UserRepository userRepository;
    private final SequenceRepository sequenceRepository;
    private final UserSequenceRepository userSequenceRepository;
    private final UserPassedMethodicsRepository passedMethodicsRepository;
    private final SequenceStatisticRepository sequenceStatisticRepository;

    @Transactional
    public void syncUsers(final List<StudentDTO> students) {
        log.info("Starting user sync.");
        deleteUsers(students); // todo clarify deletion

        students.forEach(student -> {
            Optional<User> foundUser = userRepository.findByExternalId(student.getId());
            if (foundUser.isPresent()) {
                updateUser(foundUser.get(), student);
            } else {
                createUser(student);
            }
        });
        log.info("User sync finished.");
    }

    private void createUser(StudentDTO student) {
        User usr = new User();
        usr.setCode(student.getCode());
        usr.setUsername(student.getUserName());
        usr.setExternalId(student.getId());
        userRepository.save(usr);

        List<Sequence> toBeAddedSequences = sequenceRepository.findByExternalIdIn(student.getExaminationIds());
        toBeAddedSequences.forEach(seq -> userSequenceRepository.save(new UserSequence(usr,
                                                                                       seq)));
    }

    private void deleteUsers(final List<StudentDTO> students) {
        Set<Long> studentExtIds = students.stream().map(StudentDTO :: getId).collect(Collectors.toSet());
        if (studentExtIds.isEmpty()) {
            studentExtIds.add(-1L); // add negative ID in case list is empty. Empty NOT IN clause behaves incorrectly
        }
        Set<User> usersToDelete = userRepository.findByExternalIdNotIn(studentExtIds);
        usersToDelete.removeIf(u -> u.getId().equals(User.DEFAULT_USER_ID)); // default user on device must not be deleted !
        if (usersToDelete.isEmpty()){
            log.info("No users to be deleted.");
            return;
        }

        log.info("Deleting users with ids:{}", usersToDelete.stream().map(User::getId).collect(Collectors.toList()));

        usersToDelete.forEach(usr -> {
            usr.getStatistics().forEach(stats -> {
                sequenceStatisticRepository.delete(stats);
            });
            usr.getStatistics().clear();
        });

        // delete UserSequence associations
        List<UserSequence> userSequenceTuplesToDelete = userSequenceRepository
                .findAllByUserIds(usersToDelete.stream().map(User::getId).collect(Collectors.toSet()));
        userSequenceRepository.deleteAll(userSequenceTuplesToDelete);

        // delete UserPassedMethodics associations
        List<UserPassedMethodicalSequence> userPassedMethodicsTuplesToDelete = passedMethodicsRepository
                .findAllByUserIds(usersToDelete.stream().map(User::getId).collect(Collectors.toSet()));
        passedMethodicsRepository.deleteAll(userPassedMethodicsTuplesToDelete);

        userRepository.deleteAll(usersToDelete);
    }

    private void updateUser(User user,
                            StudentDTO dto) {
        log.info("Updating user with id: {}", user.getId());
        user.setUsername(dto.getUserName());
        user.setCode(dto.getCode());
        updateUserSequencesAssociation(user, dto.getExaminationIds());
    }

    private void updateUserSequencesAssociation(User user,
                                                Set<Long> serverSequenceExternalIds) {
        log.info("Updating user - sequence associations. User id: {}", user.getId());
        // remove
        List<UserSequence> userSequencesToBeRemoved = userSequenceRepository.findAllById_UserId(user.getId());
        userSequencesToBeRemoved.removeIf(seq -> serverSequenceExternalIds.contains(seq.getSequence().getExternalId())); // remove only association from collection
        userSequenceRepository.deleteAll(userSequencesToBeRemoved);

        // add
        Set<Long> deviceSequenceExternalIds = userSequenceRepository.findAllById_UserId(user.getId())
                .stream()
                .map(UserSequence::getSequence)
                .map(Sequence::getExternalId)
                .collect(Collectors.toSet());
        Set<Long> toBeAddedSequenceExternalIds = serverSequenceExternalIds.stream()
                .filter(serverId -> !deviceSequenceExternalIds.contains(serverId))
                .collect(Collectors.toSet());
        List<Sequence> toBeAddedSequences = sequenceRepository.findByExternalIdIn(toBeAddedSequenceExternalIds);

        toBeAddedSequences.stream().forEach(seq -> {
            userSequenceRepository.save(new UserSequence(user,seq));
        });
    }

}
