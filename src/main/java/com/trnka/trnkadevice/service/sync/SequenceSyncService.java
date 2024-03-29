package com.trnka.trnkadevice.service.sync;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trnka.restapi.dto.ExaminationDto;
import com.trnka.restapi.dto.ExaminationStepDto;
import com.trnka.restapi.dto.SequenceType;
import com.trnka.trnkadevice.domain.BrailCharacter;
import com.trnka.trnkadevice.domain.LearningSequence;
import com.trnka.trnkadevice.domain.Sequence;
import com.trnka.trnkadevice.domain.SequenceStatistic;
import com.trnka.trnkadevice.domain.Step;
import com.trnka.trnkadevice.domain.TestingSequence;
import com.trnka.trnkadevice.domain.UserSequence;
import com.trnka.trnkadevice.repository.BrailCharacterRepository;
import com.trnka.trnkadevice.repository.SequenceRepository;
import com.trnka.trnkadevice.repository.SequenceStatisticRepository;
import com.trnka.trnkadevice.repository.SequenceStepRepository;
import com.trnka.trnkadevice.repository.UserSequenceRepository;
import com.trnka.trnkadevice.ui.messages.Messages;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SequenceSyncService {

    private final SequenceStatisticRepository sequenceStatisticRepository;

    private final UserSequenceRepository userSequenceRepository;
    private final SequenceRepository sequenceRepository;

    private final SequenceStepRepository sequenceStepRepository;
    private final BrailCharacterRepository brailCharacterRepository;

    @Transactional
    public void syncSequences(final List<ExaminationDto> exams) {
        log.info("Starting sequences sync.");
        deleteSequences(exams); // todo clarify deletion

        exams.forEach(exam -> {
            Optional<Sequence> foundLearningSequence = sequenceRepository.findByExternalId(exam.getId());
            if (foundLearningSequence.isPresent()) {
                updateSequence(foundLearningSequence.get(), exam);
            } else {
                createSequence(exam);
            }
        });
        log.info("User sync finished.");
    }

    private void createSequence(final ExaminationDto exam) {
        Optional<Sequence> sequenceOptional = createSequenceByType(exam);
        if (!sequenceOptional.isPresent()) {
            log.error("Skipping sequenceOptional creation due to error!");
            return;
        }
        Sequence seq = sequenceOptional.get();
        seq.setTimeout(exam.getTimeout());
        seq.setAllowedRetries(exam.getAllowedRetries());
        seq.setExternalId(exam.getId());
        addNewSequenceSteps(seq, exam);
        sequenceRepository.save(seq);
    }

    private Optional<Sequence> createSequenceByType(final ExaminationDto examinationDto) {
        switch (examinationDto.getType()) {
        case LEARNING:
            LearningSequence learningSeq = new LearningSequence();
            learningSeq.setAudioMessage(Messages.LEARNING_SEQUENCE_NAME);
            return Optional.of(learningSeq);
        case TESTING:
            TestingSequence testingSeq = new TestingSequence();
            testingSeq.setAudioMessage(Messages.TESTING_SEQUENCE_NAME);
            testingSeq.setPassingRate(examinationDto.getPassingRate());
            return Optional.of(testingSeq);
        case METHODICAL:
            log.error("Methodical sequence cannot be synced from VST !");
            return Optional.empty();
        default:
            return Optional.empty();
        }
    }

    // yet no difference between learning vs testing sequence, so common method can be used
    private void updateSequence(final Sequence sequence,
                                final ExaminationDto dto) {
        sequence.setAllowedRetries(dto.getAllowedRetries());
        sequence.setTimeout(dto.getTimeout());

        if (hasSequenceStepsChanged(sequence, dto.getSteps())) {
            // delete all statistics with steps which are referring this sequence
            deleteAllSequenceStatistics(sequence.getId());
        }

        // delete old steps
        deleteOldSequenceSteps(sequence, dto);

        // no step update, step cannot change

        // add new steps
        addNewSequenceSteps(sequence, dto);
    }

    private void addNewSequenceSteps(Sequence sequence,
                                     ExaminationDto dto) {
        List<Long> existingStepExternalIds = sequence.getSteps().stream().map(Step :: getExternalId).collect(Collectors.toList());
        Stream<ExaminationStepDto> stepsToAdd = dto.getSteps().stream().filter(step -> !existingStepExternalIds.contains(step.getId()));
        stepsToAdd.forEach(stepDto -> {
            Optional<BrailCharacter> brailOptional = brailCharacterRepository.findByLetter(stepDto.getBrailCharacter().getLetter());
            if (brailOptional.isPresent()) {
                Step step = new Step();
                step.setBrailCharacter(brailOptional.get());
                step.setExternalId(stepDto.getId());
                step.setPreserveOrder(stepDto.getPreserveOrder());
                sequence.getSteps().add(sequenceStepRepository.save(step));
            } else {
                log.error("Brail with letter {} not found in device DB! Skipping this step sync!", stepDto.getBrailCharacter().getLetter());
            }
        });
    }

    private void deleteOldSequenceSteps(Sequence sequence,
                                        ExaminationDto dto) {
        Set<Long> newSteps = dto.getSteps().stream().map(ExaminationStepDto::getId).collect(Collectors.toSet());
        List<Step> stepsToDelete = sequence.getSteps().stream().filter(step -> !newSteps.contains(step.getExternalId())).collect(Collectors.toList());
        sequence.getSteps().removeIf(step -> stepsToDelete.contains(step));
        stepsToDelete.forEach(step -> sequenceStepRepository.delete(step));
    }

    private void deleteAllSequenceStatistics(Long sequenceId) {
        List<SequenceStatistic> statisticsToDelete = sequenceStatisticRepository.findBySequence_Id(sequenceId);
        sequenceStatisticRepository.deleteAll(statisticsToDelete);
        // todo check whether relation from user site is deleted as well
    }

    private boolean hasSequenceStepsChanged(Sequence sequence,
                                            List<ExaminationStepDto> serverSteps) {
        Set<Long> deviceExternalStepIds = sequenceRepository.getSequenceStepExternalIds(sequence.getId());
        Set<Long> serverStepIds = serverSteps.stream().map(ExaminationStepDto::getId).collect(Collectors.toSet());
        return !deviceExternalStepIds.equals(serverStepIds);
    }

    private void deleteSequences(final List<ExaminationDto> exams) {
        Set<Long> examExtIds = exams.stream().map(ExaminationDto :: getId).collect(Collectors.toSet());
        if (examExtIds.isEmpty()){
            examExtIds.add(-1L); // add negative ID in case list is empty. Empty NOT IN clause behaves incorrectly
        }
        Set<Long> sequencesToDelete = sequenceRepository
                .findLearningAndTestingSequencesByExternalIdNotIn(examExtIds);
        if (sequencesToDelete.isEmpty()){
            log.info("No sequences will be deleted!");
            return;
        }

        log.info("Deleting sequences with ids:{}", sequencesToDelete);
        List<UserSequence> userSequences = userSequenceRepository.findAllBySequenceIds(sequencesToDelete);
        userSequenceRepository.deleteAll(userSequences);

        sequencesToDelete.forEach(id -> {
            deleteAllSequenceStatistics(id);
            sequenceRepository.deleteById(id);
        });
    }

}
