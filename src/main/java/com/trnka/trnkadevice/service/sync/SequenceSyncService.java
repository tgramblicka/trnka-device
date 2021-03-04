package com.trnka.trnkadevice.service.sync;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
        Optional<Sequence> sequenceOptional = createSequenceByType(exam.getType());
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

    private Optional<Sequence> createSequenceByType(final SequenceType type) {
        Sequence sequence = null;
        switch (type) {
        case LEARNING:
            sequence = new LearningSequence();
            sequence.setAudioMessage(Messages.LEARNING_SEQUENCE_NAME);
            break;
        case TESTING:
            sequence = new TestingSequence();
            sequence.setAudioMessage(Messages.TESTING_SEQUENCE_NAME);
            break;
        case METHODICAL:
            log.error("Methodical sequence cannot be synced from VST !");
            return null;
        }
        return Optional.of(sequence);
    }

    // yet no difference between learning vs testing sequence, so common method can be used
    private void updateSequence(final Sequence sequence,
                                final ExaminationDto dto) {
        sequence.setAllowedRetries(dto.getAllowedRetries());
        sequence.setTimeout(dto.getTimeout());

        if (hasSequenceStepsChanged(sequence, dto.getSteps())) {
            // delete all statistics with steps which are referring this sequence
            deleteAllSequenceStatistics(sequence);
        }

        // delete old steps
        deleteOldSequenceSteps(sequence, dto);

        // no step update, step cannot change

        // add new steps
        addNewSequenceSteps(sequence, dto);
    }

    private void addNewSequenceSteps(Sequence sequence,
                                     ExaminationDto dto) {
        for (ExaminationStepDto stepDto : dto.getSteps()) {
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
        }
    }

    private void deleteOldSequenceSteps(Sequence sequence,
                                        ExaminationDto dto) {
        Set<Long> newSteps = dto.getSteps().stream().map(ExaminationStepDto::getId).collect(Collectors.toSet());
        List<Step> stepsToDelete = sequence.getSteps().stream().filter(step -> !newSteps.contains(step.getExternalId())).collect(Collectors.toList());
        sequence.getSteps().removeIf(step -> stepsToDelete.contains(step));
        stepsToDelete.forEach(step -> sequenceStepRepository.delete(step));
    }

    private void deleteAllSequenceStatistics(Sequence sequence) {
        List<SequenceStatistic> statisticsToDelete = sequenceStatisticRepository.findBySequence_Id(sequence.getId());
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
        Set<Long> sequencesToDelete = sequenceRepository
                .findLearningAndTestingSequencesByExternalIdNotIn(exams.stream().map(ExaminationDto::getId).collect(Collectors.toSet()));
        log.info("Deleting learning sequences with ids:{}", sequencesToDelete);

        List<UserSequence> userSequences = userSequenceRepository.findAllBySequenceIds(sequencesToDelete);
        userSequences.removeAll(userSequences);

        sequencesToDelete.forEach(id -> sequenceRepository.deleteById(id));
    }

}
