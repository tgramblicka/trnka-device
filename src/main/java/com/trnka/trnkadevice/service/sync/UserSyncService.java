package com.trnka.trnkadevice.service.sync;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trnka.restapi.dto.StudentDTO;
import com.trnka.trnkadevice.domain.Sequence;
import com.trnka.trnkadevice.domain.User;
import com.trnka.trnkadevice.repository.SequenceRepository;
import com.trnka.trnkadevice.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSyncService {

    private final UserRepository userRepository;
    private final SequenceRepository sequenceRepository;

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



    private void createUser(StudentDTO student){
        User usr = new User();
        usr.setCode(student.getCode());
        usr.setUsername(student.getUserName());
        usr.setExternalId(student.getId());
        userRepository.save(usr);

        List<Sequence> toBeAddedSequences = sequenceRepository.findByExternalIdIn(student.getExaminationIds());
        usr.getSequences().addAll(toBeAddedSequences);
    }

    private void deleteUsers(final List<StudentDTO> students) {
        Set<User> usersToDelete = userRepository
                .findByExternalIdNotIn(students.stream().map(StudentDTO::getId).collect(Collectors.toSet()));
        log.info("Deleting users with ids:{}", usersToDelete.stream().map(User::getId).collect(Collectors.toList()));
        usersToDelete.removeIf(u -> u.getId().equals(User.DEFAULT_USER_ID)); // default user on device must not be deleted !
        // todo delete manually because of many-to-many issues
        usersToDelete.stream().forEach(user -> {
            user.setSequences(null);
            userRepository.deleteById(user.getId());
        });
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
        user.getSequences().removeIf(seq -> !serverSequenceExternalIds.contains(seq.getExternalId())); // remove only association from collection
        // add
        Set<Long> deviceSequenceExternalIds = user.getSequences().stream().map(Sequence :: getExternalId).collect(Collectors.toSet());
        Set<Long> toBeAddedSequenceExternalIds = serverSequenceExternalIds.stream().filter(serverId -> !deviceSequenceExternalIds.contains(serverId)).collect(
                Collectors.toSet());
        List<Sequence> toBeAddedSequences = sequenceRepository.findByExternalIdIn(toBeAddedSequenceExternalIds);
        user.getSequences().addAll(toBeAddedSequences);
    }

}
