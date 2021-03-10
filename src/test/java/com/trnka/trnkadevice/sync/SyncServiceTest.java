package com.trnka.trnkadevice.sync;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.trnka.restapi.dto.BrailCharacterDto;
import com.trnka.restapi.dto.ExaminationDto;
import com.trnka.restapi.dto.ExaminationStepDto;
import com.trnka.trnkadevice.domain.LearningSequence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import com.trnka.restapi.dto.SequenceType;
import com.trnka.restapi.dto.SyncDto;
import com.trnka.restapi.endpoint.SyncEndpoint;
import com.trnka.trnkadevice.BaseTest;
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
    private static final String SEQ_ABLE = "a,b,l,e";
    private static final String SEQ_KU = "k,u";

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

        // WHEN
        syncService.synchronize(data);

        // THEN
        Assertions.assertEquals(2, learningSequenceRepository.count());
        Assertions.assertEquals(2, testingSequenceRepository.count());

        List<UserSequence> userSequences = userSequenceRepository.findAllById_UserId(USER_ID);
        Assertions.assertEquals(4, userSequences.size());
    }


    @Test
    public void syncAfterSequenceDeletedOnVst() throws IOException {
        // FILL UP DB
        SyncDto data = SyncDataFactory.getSyncData();
        syncService.synchronize(data);

        // delete from sync data all LEARNING sequences
        data.getExaminations().removeIf(e -> e.getType().equals(SequenceType.LEARNING));

        // WHEN
        syncService.synchronize(data);
        em.clear();

        // THEN
        Assertions.assertEquals(0, learningSequenceRepository.count());
        Assertions.assertEquals(2, testingSequenceRepository.count());

        List<UserSequence> userSequences = userSequenceRepository.findAllById_UserId(USER_ID);
        Assertions.assertEquals(2, userSequences.size());
    }


    @Test
    @Transactional
    public void syncAfterSequenceStepsGotUpdatedOnVst() throws IOException {
        // FILL UP DB
        SyncDto data = SyncDataFactory.getSyncData();
        syncService.synchronize(data);

        // delete from sync data all LEARNING sequences
        ExaminationDto learningSeqDto = data.getExaminations()
                .stream()
                .filter(e -> SequenceType.LEARNING.equals(e.getType()) && SEQ_ABLE.equals(e.getName())).findFirst().get();
        String deletedLetterStep = "a";
        learningSeqDto.getSteps().removeIf(step -> step.getBrailCharacter().getLetter().equals(deletedLetterStep));
        // add new step with letter x
        String newLetterStep = "x";
        learningSeqDto.getSteps().add(examinationStep(newLetterStep, 99L));

        // WHEN
        syncService.synchronize(data);
        em.clear();

        // THEN
        Assertions.assertEquals(2, learningSequenceRepository.count());
        Assertions.assertEquals(2, testingSequenceRepository.count());

        LearningSequence learningSeq = learningSequenceRepository.findByExternalId(learningSeqDto.getId()).get();
        Assertions.assertEquals(4, learningSeq.getSteps().size());
        Assertions.assertTrue(learningSeq.getSteps().stream().anyMatch(s -> s.getBrailCharacter().getLetter().equals(newLetterStep)));
        Assertions.assertTrue(learningSeq.getSteps().stream().noneMatch(s -> s.getBrailCharacter().getLetter().equals(deletedLetterStep)));
    }

    private ExaminationStepDto examinationStep(String letter, Long id){
        ExaminationStepDto dto = new ExaminationStepDto();
        dto.setBrailCharacter(new BrailCharacterDto(letter));
        dto.setPreserveOrder(true);
        dto.setId(id);
        return dto;
    }

}
