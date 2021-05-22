package com.trnka.trnkadevice.job;

import com.trnka.restapi.dto.SyncConfigDto;
import com.trnka.restapi.endpoint.SyncEndpoint;
import com.trnka.trnkadevice.service.sync.SyncService;
import com.trnka.trnkadevice.ui.UserSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SyncJob {

    private final SyncService syncService;
    private final SyncEndpoint syncClient;
    private final UserSession userSession;

    @Scheduled(cron = "${server-sync.schedule.cron}")
    public void sync() {
        if (userSession.getUserId() != null) {
            log.warn("Syncing: Will be skipped due to logged-in user. Please logout.");
            return;
        }
        SyncConfigDto syncConfig = syncClient.getConfig();

        if (syncConfig.getEnableDownloadFromServerToDevice()) {
            // todo play sound SYNCING_FROM_SERVER_STARTED
            log.info("Syncing: Will download SyncDto from VST server.");
            syncService.synchronizeFromServer();
            // todo play sound SYNCING_FROM_SERVER_STARTED
        } else {
            log.info("Syncing: Skipping download from VST server, download sync disabled on VST!");
        }



        if (syncConfig.getEnableDownloadFromServerToDevice()) {
            // todo play sound SYNCING_TO_SERVER_STARTED
            log.info("Syncing: Will upload Student Stats to VST server.");
            syncService.synchronizeToServer();
            // todo play sound SYNCING_TO_SERVER_STARTED
        } else {
            log.info("Syncing: Skipping upload Student Stats to VST server, upload sync disabled on VST!");
        }
    }
}
