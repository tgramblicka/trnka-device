package com.trnka.trnkadevice.sync;

import com.trnka.restapi.dto.SyncDto;
import com.trnka.restapi.endpoint.SyncEndpoint;
import com.trnka.trnkadevice.BaseTest;
import com.trnka.trnkadevice.repository.SequenceRepository;
import com.trnka.trnkadevice.service.sync.SyncService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;


public class SyncServiceTest extends BaseTest {

    @Autowired
    private SyncService syncService;
    @MockBean
    private SyncEndpoint client;
    @Autowired
    private SequenceRepository sequenceRepository;

    @Test
    public void createNewTestsFromSync() throws IOException {
        // BEFORE
        SyncDto data = SyncDataFactory.getSyncData();
        Mockito.doReturn(data).when(client).syncAll();
//        Mockito.when(client.syncAll()).thenReturn(data);

        // WHEN
        syncService.synchronize();

        // THEN
        Assertions.assertTrue(sequenceRepository.count() == 4);
    }

}
