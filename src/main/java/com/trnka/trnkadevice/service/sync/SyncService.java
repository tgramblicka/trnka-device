package com.trnka.trnkadevice.service.sync;

import com.trnka.restapi.endpoint.SyncEndpoint;
import com.trnka.trnkadevice.domain.SequenceStatistic;
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

    @Transactional
    public void synchronize(final SyncDto syncDto) {
        log.info("Syncing from server: Downloaded SyncDto: {}", syncDto);
        sequenceSyncService.syncSequences(syncDto.getExaminations());
        log.info("Syncing from server: Sequences sync finished!");
        userSyncService.syncUsers(syncDto.getStudents());
        log.info("Syncing from server: Users sync finished!");
        sendExaminationStatistics();
        log.info("Syncing to server: Student statistics sync finished!");
    }

    public void sendExaminationStatistics() {
        LocalDateTime lastStatisticUpdateToServerRun = synchronizationRepository.findLastSyncRun(SyncType.UPDATED_EXAMINATION_STATISTICS_TO_SERVER)
                .stream()
                .findFirst()
                .map(Synchronization::getExecutedOn)
                .orElse(LocalDateTime.MIN);
        log.info("Last run of statistic update to server was on: {}.", lastStatisticUpdateToServerRun);
        List<SequenceStatistic> sequencStats = sequenceStatisticRepository.findAllByCreatedOnAfter(lastStatisticUpdateToServerRun);
        log.info("Cince last sync on {} , found {} sequence statistics due to be synced to server, will sync to server now!", lastStatisticUpdateToServerRun);

        syncClient.updateExaminationStatisticsToAllStudents(new StatsMapper().mapToStatisticsSyncDto(sequencStats));
    }

}
