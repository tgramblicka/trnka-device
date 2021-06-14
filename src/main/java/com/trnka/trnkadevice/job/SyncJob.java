package com.trnka.trnkadevice.job;

import com.trnka.restapi.dto.SyncConfigDto;
import com.trnka.restapi.endpoint.SyncEndpoint;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.service.sync.SyncService;
import com.trnka.trnkadevice.ui.UserSession;
import com.trnka.trnkadevice.ui.messages.Messages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class SyncJob {

    private final SyncService syncService;
    private final SyncEndpoint syncClient;
    private final UserSession userSession;
    private final IRenderer renderer;

    @Scheduled(cron = "${server-sync.schedule.cron}")
    public void sync() {
        if (userSession.getUserId() != null) {
            log.warn("Syncing: Will be skipped due to logged-in user. Please logout.");
            return;
        }
        SyncConfigDto syncConfig = syncClient.getConfig();

        syncFromServer(syncConfig);
        syncToServer(syncConfig);
    }

    private void syncToServer(final SyncConfigDto syncConfig) {
        if (syncConfig.getEnableUploadFromDeviceToServer()) {
            renderer.renderMessages(Collections.singletonList(Messages.SYNCING_TO_SERVER_STARTED));
            log.info("Syncing: Will upload Student Stats to VST server.");
            try {
                syncService.synchronizeToServer();
                renderer.renderMessages(Collections.singletonList(Messages.SYNCING_TO_SERVER_FINISHED));
            } catch (Exception e) {
                log.error("Error occurred during upload Student Stats to VST server: {}", e);
                renderer.renderMessages(Collections.singletonList(Messages.SYNCING_TO_SERVER_FAILED));
            }
        } else {
            log.info("Syncing: Skipping upload Student Stats to VST server, upload sync disabled on VST!");
        }
    }

    private void syncFromServer(final SyncConfigDto syncConfig) {
        if (syncConfig.getEnableDownloadFromServerToDevice()) {
            renderer.renderMessages(Collections.singletonList(Messages.SYNCING_FROM_SERVER_STARTED));
            log.info("Syncing: Will download SyncDto from VST server.");
            try {
                syncService.synchronizeFromServer();
                renderer.renderMessages(Collections.singletonList(Messages.SYNCING_FROM_SERVER_FINISHED));
            } catch (Exception e) {
                log.error("Error occurred during sync from server: {}", e);
                renderer.renderMessages(Collections.singletonList(Messages.SYNCING_FROM_SERVER_FAILED));
            }
        } else {
            log.info("Syncing: Skipping download from VST server, download sync disabled on VST!");
        }
    }
}
