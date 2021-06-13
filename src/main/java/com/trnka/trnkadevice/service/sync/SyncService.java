package com.trnka.trnkadevice.service.sync;

import com.trnka.restapi.endpoint.SyncEndpoint;
import com.trnka.trnkadevice.domain.SequenceStatistic;
import com.trnka.trnkadevice.domain.SyncStatus;
import com.trnka.trnkadevice.domain.SyncType;
import com.trnka.trnkadevice.domain.Synchronization;
import com.trnka.trnkadevice.repository.SequenceStatisticRepository;
import com.trnka.trnkadevice.repository.SynchronizationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.trnka.restapi.dto.SyncDto;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SyncService {

    private final UserSyncService userSyncService;
    private final SequenceSyncService sequenceSyncService;
    private final SequenceStatisticRepository sequenceStatisticRepository;
    private final SynchronizationRepository synchronizationRepository;
    private final SyncEndpoint syncClient;

    @Value("${device.id}")
    private String deviceId;



    public void syncFromServerAndThenToServer(){
        synchronizeFromServer();
        synchronizeToServer();
    }


    public void synchronizeFromServer() {
        SyncDto syncDto = syncClient.getSyncDto();
        synchronizeFromServer(syncDto);
    }


    @Transactional
    public void synchronizeFromServer(final SyncDto syncDto) {
        log.info("Syncing from server: Downloaded SyncDto: {}", syncDto);
        sequenceSyncService.syncSequences(syncDto.getExaminations());
        log.info("Syncing from server: Sequences sync finished!");
        userSyncService.syncUsers(syncDto.getStudents());
        log.info("Syncing from server: Users sync finished!");
        saveSyncFromServerEvent(SyncStatus.SUCCESS);
    }

    @Transactional
    public void synchronizeToServer() {
        sendExaminationStatistics();
        log.info("Syncing to server: Student statistics sync finished!");
    }

    private void sendExaminationStatistics() {
        LocalDateTime lastStatisticUpdateToServerRun = synchronizationRepository.findLastSuccessfulSyncRun(SyncType.UPDATED_EXAMINATION_STATISTICS_TO_SERVER)
                .stream()
                .findFirst()
                .map(Synchronization::getExecutedOn)
                .orElse(LocalDateTime.MIN);
        log.info("Last run of statistic update to server was on: {}.", lastStatisticUpdateToServerRun);
        List<SequenceStatistic> sequencStats = sequenceStatisticRepository.findAllByCreatedOnAfter(lastStatisticUpdateToServerRun);
        log.info("Since last sync on {} , found {} sequence statistics due to be synced to server, will sync to server now!", lastStatisticUpdateToServerRun, sequencStats.size());

        try {
            Boolean updated = syncClient.updateExaminationStatisticsToAllStudents(new StatsMapper().mapToStatisticsSyncDto(sequencStats, deviceId));
            if (updated) {
                saveStudentStatsSyncEvent(SyncStatus.SUCCESS);
                return;
            }
            saveStudentStatsSyncEvent(SyncStatus.ERROR);
        } catch (Exception e) {
            log.error("Exception occurred during update of Student statistics: {}", e);
            saveStudentStatsSyncEvent(SyncStatus.ERROR);
            return;
        }
    }

    private void saveStudentStatsSyncEvent(final SyncStatus status) {
        Synchronization sync = new Synchronization(LocalDateTime.now(), SyncType.UPDATED_EXAMINATION_STATISTICS_TO_SERVER, status);
        synchronizationRepository.save(sync);
    }

    private void saveSyncFromServerEvent(final SyncStatus status) {
        Synchronization sync = new Synchronization(LocalDateTime.now(), SyncType.SYNCED_ALL_FROM_SERVER, status);
        synchronizationRepository.save(sync);
    }

}
