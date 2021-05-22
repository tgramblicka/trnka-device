package com.trnka.trnkadevice.controller;

import com.trnka.trnkadevice.job.SyncJob;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trnka.trnkadevice.service.sync.SyncService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "sync")
@RequiredArgsConstructor
@Slf4j
public class SyncController {

    private final SyncService syncService;
    private final SyncJob syncJob;

    @GetMapping(path = "download")
    public String download() {
        log.info("Syncing: Will download SyncDto from VST server.");
        syncService.synchronizeFromServer();
        return "synced from VST";
    }

    @GetMapping(path = "upload")
    public String upload() {
        log.info("Syncing: Will upload Student Stats to VST server.");
        syncService.synchronizeToServer();
        return "synced to VST";
    }

    @GetMapping(path = "all")
    public String all() {
        syncService.syncFromServerAndThenToServer();
        return "synced ALL";
    }


    @GetMapping(path = "simulate-scheduled-job")
    public String simulateScheduledJob() {
        syncJob.sync();
        return "Scheduled Sync Job started";
    }

}
