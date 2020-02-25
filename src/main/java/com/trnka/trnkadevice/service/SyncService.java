package com.trnka.trnkadevice.service;

import com.trnka.restapi.dto.StudentDTO;
import com.trnka.restapi.dto.SyncDto;
import com.trnka.restapi.endpoint.StudentSyncEndpoint;
import com.trnka.trnkadevice.domain.User;
import com.trnka.trnkadevice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SyncService {

    private StudentSyncEndpoint studentSyncEndpoint;
    private UserRepository userRepository;

    public SyncService(final StudentSyncEndpoint studentSyncEndpoint,
                       final UserRepository userRepository) {
        this.studentSyncEndpoint = studentSyncEndpoint;
        this.userRepository = userRepository;
    }

    public void syncronize() {
        SyncDto syncDto = studentSyncEndpoint.syncAll();

        syncPureUsers(syncDto);
        System.out.println(syncDto);
    }

    private void syncPureUsers(final SyncDto syncDto) {
        // deleteStudents(syncDto);

        syncDto.getStudents().forEach(student -> {
            Optional<User> foundUser = userRepository.findByCode(student.getCode());
            if (foundUser.isPresent()) {
                // update examinations
            } else {
                User usr = new User();
                usr.setCode(student.getCode());
                usr.setUsername(student.getUserName());
                userRepository.save(usr);
            }

        });
    }

    private void deleteStudents(final SyncDto syncDto) {
        List<User> usersToDelete = userRepository
                .findAllWithDifferentCodes(syncDto.getStudents().stream().map(StudentDTO::getCode).collect(Collectors.toList()));
    }

}
