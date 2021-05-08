package com.trnka.trnkadevice.service.sync;

import com.trnka.restapi.endpoint.SyncEndpoint;
import com.trnka.trnkadevice.domain.SequenceStatistic;
import com.trnka.trnkadevice.domain.SyncStatus;
import com.trnka.trnkadevice.domain.SyncType;
import com.trnka.trnkadevice.domain.Synchronization;
import com.trnka.trnkadevice.repository.SequenceStatisticRepository;
import com.trnka.trnkadevice.repository.SynchronizationRepository;
import lombok.extern.slf4j.Slf4j;
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
            Boolean updated = syncClient.updateExaminationStatisticsToAllStudents(new StatsMapper().mapToStatisticsSyncDto(sequencStats));
            if (updated) {
                saveStudentStatsSync(SyncStatus.SUCCESS);
                return;
            }
            saveStudentStatsSync(SyncStatus.ERROR);
        } catch (Exception e) {
            log.error("Exception occurred during update of Student statistics: {}", e);
            saveStudentStatsSync(SyncStatus.ERROR);
            return;
        }
    }

    private void saveStudentStatsSync(final SyncStatus error) {
        Synchronization sync = new Synchronization(LocalDateTime.now(), SyncType.UPDATED_EXAMINATION_STATISTICS_TO_SERVER, error);
        synchronizationRepository.save(sync);
    }

}
