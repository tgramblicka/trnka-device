package com.trnka.trnkadevice.service;

import com.trnka.restapi.dto.SyncDto;
import com.trnka.restapi.endpoint.StudentSyncEndpoint;
import org.springframework.stereotype.Service;

@Service
public class SyncService {

    private StudentSyncEndpoint studentSyncEndpoint;

    public SyncService(final StudentSyncEndpoint studentSyncEndpoint) {
        this.studentSyncEndpoint = studentSyncEndpoint;
    }

    public void syncronize() {
        SyncDto syncDto = studentSyncEndpoint.syncAll();
        System.out.println(syncDto);
    }

}
