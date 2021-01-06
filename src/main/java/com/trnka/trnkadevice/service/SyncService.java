package com.trnka.trnkadevice.service;

import com.trnka.restapi.dto.StudentDTO;
import com.trnka.restapi.dto.SyncDto;
import com.trnka.restapi.dto.statistics.DeviceStatisticsSyncDto;
import com.trnka.restapi.endpoint.SyncEndpoint;
import com.trnka.trnkadevice.domain.User;
import com.trnka.trnkadevice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SyncService {

    private SyncEndpoint studentSyncEndpoint;
    private UserRepository userRepository;

    public SyncService(final SyncEndpoint studentSyncEndpoint,
                       final UserRepository userRepository) {
        this.studentSyncEndpoint = studentSyncEndpoint;
        this.userRepository = userRepository;
    }


    public void syncronize() {
        SyncDto syncDto = studentSyncEndpoint.syncAll();

        syncPureUsers(syncDto);
        // todo sync tests
        // todo save into sync table
        System.out.println(syncDto);
    }

    public void sendExaminationStatistics(){
        // todo implement
        // send only those examination stats, where updatedOn > latest synchronization.executed_on where type=UPDATED_EXAMINATION_STATISTICS_ON_SERVER
        studentSyncEndpoint.updateExaminationStatisticsToAllStudents(new DeviceStatisticsSyncDto());
    }

    private void syncPureUsers(final SyncDto syncDto) {
        // deleteStudents(syncDto); // todo clarify deletion

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
