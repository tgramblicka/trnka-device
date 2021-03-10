package com.trnka.trnkadevice.service.sync;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.trnka.restapi.dto.SyncDto;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SyncService {

    private final UserSyncService userSyncService;
    private final SequenceSyncService sequenceSyncService;

    @Transactional
    public void synchronize(final SyncDto syncDto) {
        log.info("Syncing: Downloaded SyncDto: {}", syncDto);
        sequenceSyncService.syncSequences(syncDto.getExaminations());
        // transaction must be commited here
        userSyncService.syncUsers(syncDto.getStudents());
        // todo sync tests
        // todo save into sync table
        System.out.println(syncDto);
    }

    public void sendExaminationStatistics() {
        // todo implement
        // send only those examination stats, where updatedOn > latest synchronization.executed_on where type=UPDATED_EXAMINATION_STATISTICS_ON_SERVER
        // client.updateExaminationStatisticsToAllStudents(new DeviceStatisticsSyncDto());
    }

}
