package com.trnka.trnkadevice.sync;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import com.trnka.restapi.dto.SyncDto;
import com.trnka.restapi.endpoint.SyncEndpoint;
import com.trnka.trnkadevice.BaseTest;
import com.trnka.trnkadevice.domain.User;
import com.trnka.trnkadevice.domain.UserSequence;
import com.trnka.trnkadevice.repository.LearningSequenceRepository;
import com.trnka.trnkadevice.repository.SequenceRepository;
import com.trnka.trnkadevice.repository.TestingSequenceRepository;
import com.trnka.trnkadevice.repository.UserRepository;
import com.trnka.trnkadevice.repository.UserSequenceRepository;
import com.trnka.trnkadevice.service.sync.SyncService;

public class SyncServiceTest extends BaseTest {

    @Autowired
    private SyncService syncService;
    @MockBean
    private SyncEndpoint client;
    @Autowired
    private SequenceRepository sequenceRepository;
    @Autowired
    private TestingSequenceRepository testingSequenceRepository;
    @Autowired
    private LearningSequenceRepository learningSequenceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserSequenceRepository userSequenceRepository;
    @PersistenceContext
    private EntityManager em;

    private static final Long USER_ID = 1L;

    @BeforeEach
    @Transactional
    public void cleanupDb() {
        deleteAllLearningAndTestingSequnces();
    }

    private void deleteAllLearningAndTestingSequnces() {
        userSequenceRepository.deleteAll();
        learningSequenceRepository.deleteAll();
        testingSequenceRepository.deleteAll();
    }

    @Test
    public void createNewTestsFromSync() throws IOException {
        // BEFORE
        SyncDto data = SyncDataFactory.getSyncData();
        Mockito.doReturn(data).when(client).syncAll();
        // Mockito.when(client.syncAll()).thenReturn(data);

        // WHEN
        syncService.synchronize();

        // THEN
        Assertions.assertEquals(2, learningSequenceRepository.count());
        Assertions.assertEquals(2, testingSequenceRepository.count());

        User user = userRepository.findById(USER_ID).get();
        List<UserSequence> userSequences = userSequenceRepository.findAllById_UserId(USER_ID);
        Assertions.assertEquals(4, userSequences.size());
    }

}
