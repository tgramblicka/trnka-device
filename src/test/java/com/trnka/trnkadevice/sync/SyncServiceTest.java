package com.trnka.trnkadevice.sync;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import com.trnka.restapi.dto.BrailCharacterDto;
import com.trnka.restapi.dto.ExaminationDto;
import com.trnka.restapi.dto.ExaminationStepDto;
import com.trnka.restapi.dto.SequenceType;
import com.trnka.restapi.dto.StudentDTO;
import com.trnka.restapi.dto.SyncDto;
import com.trnka.restapi.endpoint.SyncEndpoint;
import com.trnka.trnkadevice.BaseTest;
import com.trnka.trnkadevice.domain.LearningSequence;
import com.trnka.trnkadevice.domain.Sequence;
import com.trnka.trnkadevice.domain.SequenceStatistic;
import com.trnka.trnkadevice.domain.StepStatistic;
import com.trnka.trnkadevice.domain.User;
import com.trnka.trnkadevice.domain.UserSequence;
import com.trnka.trnkadevice.repository.LearningSequenceRepository;
import com.trnka.trnkadevice.repository.SequenceRepository;
import com.trnka.trnkadevice.repository.SequenceStatisticRepository;
import com.trnka.trnkadevice.repository.SequenceStepRepository;
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
    @Autowired
    private SequenceStatisticRepository sequenceStatisticRepository;


    @PersistenceContext
    private EntityManager em;

    private static final Long STUDENT_EXT_ID = 2L;
    private static final Long SEQ_LEARNING_ABLE_EXT_ID = 10L;
    private static final Long SEQ_LEARNING_KU_EXT_ID = 11L;

    private static final Long SEQ_TESTING_ABLE_EXT_ID = 12L;
    private static final Long SEQ_TESTING_KU_EXT_ID = 13L;
    private static final Long SEQ_TESTING_COMI_EXT_ID = 14L;
    private static final Long SEQ_TESTING_VACR_EXT_ID = 15L;
    private static final String SEQ_ABLE = "a,b,l,e";
    private static final String SEQ_KU = "k,u";

    @BeforeEach
    @Transactional
    public void cleanupDb() {
        deleteAllUsersAndLearningAndTestingSequences();
    }

    private void deleteAllUsersAndLearningAndTestingSequences() {
        userSequenceRepository.deleteAll();
        learningSequenceRepository.deleteAll();
        testingSequenceRepository.deleteAll();
    }

    @Test
    @Transactional
    public void createNewTestsFromSync() throws IOException {
        // BEFORE
        SyncDto data = SyncDataFactory.getSyncData();

        long originalLearningSeqCount = data.getExaminations().stream().filter(e -> e.getType().equals(SequenceType.LEARNING)).count();
        long originalTestingSeqCount = data.getExaminations().stream().filter(e -> e.getType().equals(SequenceType.TESTING)).count();

        // WHEN
        syncService.synchronize(data);

        // THEN
        Assertions.assertEquals(originalLearningSeqCount, learningSequenceRepository.count());
        Assertions.assertEquals(originalTestingSeqCount, testingSequenceRepository.count());

        User newUser = userRepository.findByExternalId(STUDENT_EXT_ID).get();
        List<UserSequence> userSequences = userSequenceRepository.findAllById_UserId(newUser.getId());
        Assertions.assertEquals(4, userSequences.size());
    }



    public Long getStudentCountOfExamsFromSyncDto(SyncDto data, Long userExtId, SequenceType sequenceType){
        Set<Long> studentExaminationIds = data.getStudents().stream().filter(u -> u.getId().equals(userExtId)).findFirst().get().getExaminationIds();
        return data.getExaminations().stream().filter(e -> e.getType().equals(sequenceType) && studentExaminationIds.contains(e.getId())).count();
    }

    @Test
    @Transactional
    public void syncAfterSequenceDeletedOnVst() throws IOException {
        // FILL UP DB
        SyncDto data = SyncDataFactory.getSyncData();
        syncService.synchronize(data);
        long originalTestingSeqCount = data.getExaminations().stream().filter(e -> e.getType().equals(SequenceType.TESTING)).count();


        long originalUserTestingExamCount = getStudentCountOfExamsFromSyncDto(data,STUDENT_EXT_ID, SequenceType.TESTING);


        // delete ALL sync data all LEARNING sequences
        data.getExaminations().removeIf(e -> e.getType().equals(SequenceType.LEARNING));

        // WHEN
        syncService.synchronize(data);
        em.clear();

        // THEN
        Assertions.assertEquals(0, learningSequenceRepository.count());
        Assertions.assertEquals(originalTestingSeqCount, testingSequenceRepository.count());

        User newUser = userRepository.findByExternalId(STUDENT_EXT_ID).get();
        List<UserSequence> userSequences = userSequenceRepository.findAllById_UserId(newUser.getId());
        Assertions.assertEquals(originalUserTestingExamCount, userSequences.size());
    }

    @Test
    @Transactional
    public void syncAfterSequenceStepsGotUpdatedOnVst() throws IOException {
        // FILL UP DB
        SyncDto data = SyncDataFactory.getSyncData();
        long originalLearningSeqCount = data.getExaminations().stream().filter(e -> e.getType().equals(SequenceType.LEARNING)).count();
        long originalTestingSeqCount = data.getExaminations().stream().filter(e -> e.getType().equals(SequenceType.TESTING)).count();

        syncService.synchronize(data);

        // add user statistics for ABLE
        User user = userRepository.findByExternalId(STUDENT_EXT_ID).get();
        sequenceStatisticRepository.save(sequenceStatistic(user, SEQ_LEARNING_ABLE_EXT_ID));

        // delete from sync data all LEARNING sequences
        ExaminationDto learningSeqDto = data.getExaminations()
                .stream()
                .filter(e -> SequenceType.LEARNING.equals(e.getType()) && SEQ_ABLE.equals(e.getName()))
                .findFirst()
                .get();

        // replace sequence step from ABLE to XBLE
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
        Assertions.assertEquals(originalLearningSeqCount, learningSequenceRepository.count());
        Assertions.assertEquals(originalTestingSeqCount, testingSequenceRepository.count());

        LearningSequence learningSeq = learningSequenceRepository.findByExternalId(learningSeqDto.getId()).get();
        Assertions.assertEquals(4, learningSeq.getSteps().size());
        Assertions.assertTrue(learningSeq.getSteps().stream().anyMatch(s -> s.getBrailCharacter().getLetter().equals(newLetterStep)));
        Assertions.assertTrue(learningSeq.getSteps().stream().noneMatch(s -> s.getBrailCharacter().getLetter().equals(deletedLetterStep)));
        // ABLE sequence changed to XBLE therefore old statistics must've been deleted !
        Assertions.assertEquals(0, user.getStatistics(sequenceStatisticRepository).size());
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

        User user = userRepository.findByExternalId(STUDENT_EXT_ID).get();

        // add user statistics just test whether they will be deleted
        sequenceStatisticRepository.save(sequenceStatistic(user, SEQ_LEARNING_ABLE_EXT_ID));

        // remove user from syncData
        data.getStudents().removeIf(s -> s.getId().equals(STUDENT_EXT_ID));

        // sync again, above removed user should be deleted with his statistics...
        syncService.synchronize(data);


        em.flush();
        em.clear();
        // 1st user which is in DB, is a default one from liquibase scripts, another one was deleted by sync
        Assertions.assertEquals(1, userRepository.count());
    }

    @Test
    @Transactional
    public void syncAfterStudentExaminationsMovedToAnotherCourseOnVst() throws IOException {
        // Fill DB with students
        SyncDto data = SyncDataFactory.getSyncData();
        syncService.synchronize(data);

        User user = userRepository.findByExternalId(STUDENT_EXT_ID).get();
        int originalUserSeqCount = user.getAllSequences(userSequenceRepository).size();

        // add student statistics just test whether they will be deleted
        sequenceStatisticRepository.save(sequenceStatistic(user, SEQ_LEARNING_ABLE_EXT_ID));

        em.flush();
        em.clear();

        // update student in SyncDTO
        StudentDTO studentDto = data.getStudents().stream().filter(s -> s.getId().equals(STUDENT_EXT_ID)).findFirst().get();
        studentDto.getExaminationIds().remove(SEQ_TESTING_ABLE_EXT_ID);
        studentDto.getExaminationIds().remove(SEQ_TESTING_KU_EXT_ID);

        studentDto.getExaminationIds().add(SEQ_TESTING_COMI_EXT_ID);
        studentDto.getExaminationIds().add(SEQ_TESTING_VACR_EXT_ID);

        // sync again (now with removed and added new sequences)
        syncService.synchronize(data);

        em.flush();
        em.clear();

        User updatedUser = userRepository.findByExternalId(STUDENT_EXT_ID).get();
        List<UserSequence> newUserSequences = updatedUser.getAllSequences(userSequenceRepository);

        // sequence count did not change 2 deleted, 2 added
        Assertions.assertEquals(originalUserSeqCount, newUserSequences.size());
        Assertions.assertTrue(newUserSequences.stream().anyMatch(seq -> seq.getSequence().getExternalId().equals(SEQ_TESTING_COMI_EXT_ID)));
        Assertions.assertTrue(newUserSequences.stream().anyMatch(seq -> seq.getSequence().getExternalId().equals(SEQ_TESTING_VACR_EXT_ID)));
        Assertions.assertEquals(1, user.getStatistics(sequenceStatisticRepository).size()); // old sequence Statistic will be preserved, because it is still in the DB
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
        stat.setCreatedOn(LocalDateTime.now());
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
