package com.trnka.trnkadevice.service.sync;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.trnka.restapi.dto.SyncDto;
import com.trnka.restapi.dto.statistics.DeviceStatisticsSyncDto;
import com.trnka.restapi.endpoint.SyncEndpoint;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class SyncService {

    private final SyncEndpoint studentSyncEndpoint;
    private final UserSyncService userSyncService;
    private final SequenceSyncService sequenceSyncService;


    public void synchronize() {
        log.info("Syncing: Will download SyncDto from VST server.");
        SyncDto syncDto = studentSyncEndpoint.syncAll();
        log.info("Syncing: Downloaded SyncDto: {}", syncDto);

        sequenceSyncService.syncSequences(syncDto.getExaminations());
        // transaction must be commited here
        userSyncService.syncUsers(syncDto.getStudents());
        // todo sync tests
        // todo save into sync table
        System.out.println(syncDto);
    }

    public void sendExaminationStatistics(){
        // todo implement
        // send only those examination stats, where updatedOn > latest synchronization.executed_on where type=UPDATED_EXAMINATION_STATISTICS_ON_SERVER
        studentSyncEndpoint.updateExaminationStatisticsToAllStudents(new DeviceStatisticsSyncDto());
    }







}
