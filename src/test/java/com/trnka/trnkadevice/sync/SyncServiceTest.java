package com.trnka.trnkadevice.sync;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.trnka.restapi.dto.BrailCharacterDto;
import com.trnka.restapi.dto.ExaminationDto;
import com.trnka.restapi.dto.ExaminationStepDto;
import com.trnka.trnkadevice.domain.LearningSequence;
import com.trnka.trnkadevice.domain.Sequence;
import com.trnka.trnkadevice.domain.SequenceStatistic;
import com.trnka.trnkadevice.domain.StepStatistic;
import com.trnka.trnkadevice.domain.User;
import com.trnka.trnkadevice.repository.SequenceStepRepository;
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
    private SequenceStepRepository stepRepository;
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

    private static final Long STUDENT_EXT_ID = 2L;
    private static final Long SEQ_LEARNING_ABLE_EXT_ID = 10L;
    private static final String SEQ_ABLE = "a,b,l,e";
    private static final String SEQ_KU = "k,u";

    @BeforeEach
    @Transactional
    public void cleanupDb() {
        deleteAllUsersAndLearningAndTestingSequnces();
    }

    private void deleteAllUsersAndLearningAndTestingSequnces() {
        userSequenceRepository.deleteAll();
        learningSequenceRepository.deleteAll();
        testingSequenceRepository.deleteAll();
    }

    @Test
    @Transactional
    public void createNewTestsFromSync() throws IOException {
        // BEFORE
        SyncDto data = SyncDataFactory.getSyncData();

        // WHEN
        syncService.synchronize(data);

        // THEN
        Assertions.assertEquals(2, learningSequenceRepository.count());
        Assertions.assertEquals(2, testingSequenceRepository.count());

        User newUser = userRepository.findByExternalId(STUDENT_EXT_ID).get();
        List<UserSequence> userSequences = userSequenceRepository.findAllById_UserId(newUser.getId());
        Assertions.assertEquals(4, userSequences.size());
    }

    @Test
    @Transactional
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

        User newUser = userRepository.findByExternalId(STUDENT_EXT_ID).get();
        List<UserSequence> userSequences = userSequenceRepository.findAllById_UserId(newUser.getId());
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
                .filter(e -> SequenceType.LEARNING.equals(e.getType()) && SEQ_ABLE.equals(e.getName()))
                .findFirst()
                .get();
        String deletedLetterStep = "a";
        learningSeqDto.getSteps().removeIf(step -> step.getBrailCharacter().getLetter().equals(deletedLetterStep));
        // add new step with letter x
        String newLetterStep = "x";
        learningSeqDto.getSteps().add(examinationStep(newLetterStep, 99L));

        // WHEN
        syncService.synchronize(data);
        em.flush();
        em.clear();

        // THEN
        Assertions.assertEquals(2, learningSequenceRepository.count());
        Assertions.assertEquals(2, testingSequenceRepository.count());

        LearningSequence learningSeq = learningSequenceRepository.findByExternalId(learningSeqDto.getId()).get();
        Assertions.assertEquals(4, learningSeq.getSteps().size());
        Assertions.assertTrue(learningSeq.getSteps().stream().anyMatch(s -> s.getBrailCharacter().getLetter().equals(newLetterStep)));
        Assertions.assertTrue(learningSeq.getSteps().stream().noneMatch(s -> s.getBrailCharacter().getLetter().equals(deletedLetterStep)));
    }

    @Test
    @Transactional
    public void createNewStudentFromSync() throws IOException {
        // BEFORE
        SyncDto data = SyncDataFactory.getSyncData();
        syncService.synchronize(data);

        // 1st user is a default one from liquibase scripts, another one was added by sync
        Assertions.assertEquals(2, userRepository.count());
        Assertions.assertTrue(userRepository.findByExternalId(2L).isPresent());
    }

    @Test
    @Transactional
    public void syncAfterStudentDeletedOnVst() throws IOException {
        // Fill DB with students
        SyncDto data = SyncDataFactory.getSyncData();
        syncService.synchronize(data);

        User student = userRepository.findByExternalId(STUDENT_EXT_ID).get();

        // add student statistics
        student.getStatistics().add(sequenceStatistic(student, SEQ_LEARNING_ABLE_EXT_ID));

        // remove student from syncData
        data.getStudents().removeIf(s -> s.getId().equals(STUDENT_EXT_ID));
        syncService.synchronize(data); // sync again


        em.flush();
        em.clear();
        // 1st user is a default one from liquibase scripts, another one was deleted by sync
        Assertions.assertEquals(1, userRepository.count());
    }

    private ExaminationStepDto examinationStep(String letter,
                                               Long id) {
        ExaminationStepDto dto = new ExaminationStepDto();
        dto.setBrailCharacter(new BrailCharacterDto(letter));
        dto.setPreserveOrder(true);
        dto.setId(id);
        return dto;
    }

    private SequenceStatistic sequenceStatistic(User user, Long sequenceExternalId) {
        Sequence sequence = sequenceRepository.findByExternalId(sequenceExternalId).get();
        SequenceStatistic stat = new SequenceStatistic();
        stat.setCreatedOn(new Date());
        stat.setSequence(sequence);
        stat.setUser(user);

        sequence.getSteps().forEach(step -> {
            stat.getStepStats().add(stepStatistic(step.getId()));
        });
        return stat;
    }

    private StepStatistic stepStatistic(Long stepId){
        StepStatistic stepStat = new StepStatistic();
        stepStat.setCorrect(true);
        stepStat.setRetries(1);
        stepStat.setStep(stepRepository.findById(stepId).get());
        return stepStat;

    }


}
