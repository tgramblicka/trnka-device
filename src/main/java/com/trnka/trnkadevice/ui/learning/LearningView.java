package com.trnka.trnkadevice.ui.learning;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.trnka.trnkadevice.domain.LearningSequence;
import com.trnka.trnkadevice.domain.Sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.domain.Step;
import com.trnka.trnkadevice.domain.statistics.SequenceStatistic;
import com.trnka.trnkadevice.domain.statistics.StepStatistic;
import com.trnka.trnkadevice.inputreader.InputReader;
import com.trnka.trnkadevice.inputreader.Keystroke;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.repository.SequenceStatisticRepository;
import com.trnka.trnkadevice.ui.IView;
import com.trnka.trnkadevice.ui.messages.Messages;
import com.trnka.trnkadevice.ui.navigation.Navigator;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LearningView implements IView {

    private IRenderer renderer;
    private LearningSequenceComponent learningSequenceComponent;
    private InputReader inputReader;
    private Navigator navigator;
    private SequenceStatisticRepository sequenceStatisticRepository;

    @Autowired
    public LearningView(final IRenderer renderer,
                        final InputReader inputReader,
                        final Navigator navigator,
                        final SequenceStatisticRepository sequenceStatisticRepository) {
        this.renderer = renderer;
        this.inputReader = inputReader;
        this.navigator = navigator;
        this.sequenceStatisticRepository = sequenceStatisticRepository;
    }

    public void refresh(final LearningSequenceComponent learningSequenceComponent) {
        this.learningSequenceComponent = learningSequenceComponent;
    }

    @Override
    public void enter() {

        if (learningSequenceComponent == null) {
            log.error("Learning sequence component is null, this CANNOT HAPPEN");
            return;
        }
        this.renderer.renderLabel(learningSequenceComponent);

        LearningSequence seq = this.learningSequenceComponent.getSequence();
        SequenceStatistic seqStats = createStatistic(seq);
        for (Step step : seq.getSteps()) {
            renderer.renderMessage(Messages.LEARNING_TYPE_IN_CHARACTER_BRAIL, step.getBrailCharacter().getLetter());
            long start = System.currentTimeMillis();
            Integer negativeRetries = 0;
            StepStatistic stepStats = new StepStatistic();
            boolean isCorrect = evaluateUserInput(step, stepStats, seq.getAllowedRetries(), negativeRetries);

            stepStats.setStep(step);
            stepStats.setTook(System.currentTimeMillis() - start);
            stepStats.setCorrect(isCorrect);
            stepStats.setRetries(negativeRetries);
            seqStats.getStepStats().add(stepStats);
        }

        renderer.renderMessage(Messages.LEARNING_SEQUENCE_END);

        sequenceStatisticRepository.save(seqStats);

        navigator.navigate(LearningSequenceSelectionView.class);
    }

    private SequenceStatistic createStatistic(final Sequence seq) {
        SequenceStatistic seqStats = new SequenceStatistic();
        seqStats.setSequenceId(seq.getId());
        seqStats.setCreatedOn(new Date());
        return seqStats;
    }

    private List<Keystroke> readInputKeystrokes() {
        List<Keystroke> keyStrokes = new ArrayList<>();
        Keystroke keystroke = inputReader.readFromInput();

        while (!keystroke.equals(Keystroke.SUBMIT)) {
            log.info(keystroke.getValue());
            keyStrokes.add(keystroke);
            keystroke = inputReader.readFromInput();
        }
        return keyStrokes;
    }

    private boolean evaluateUserInput(Step step,
                                      StepStatistic stepStats,
                                      int maxAllowedTries,
                                      int negativeTries) {
        if (negativeTries == maxAllowedTries) {
            return false;
        }
        List<Keystroke> keystrokes = readInputKeystrokes();
        boolean isCorrect = step.getBrailCharacter().getBrailRepresentationKeystrokes().equals(keystrokes);
        if (isCorrect) {
            renderer.renderMessage(Messages.LEARNING_CORRECT_CHARACTER_BRAIL_SEQUENCE_SUBMITTED);
            return true;
        } else {
            negativeTries++;
            stepStats.setRetries(negativeTries);
            renderer.renderMessage(Messages.LEARNING_INCORRECT_CHARACTER_BRAIL_SEQUENCE_SUBMITTED,
                    step.getBrailCharacter().getBrailRepresentation().stream().map(String::valueOf).collect(Collectors.joining(",")),
                    String.valueOf(maxAllowedTries - negativeTries));
            return evaluateUserInput(step, stepStats, maxAllowedTries, negativeTries);
        }
    }

    @Override
    public Messages getLabel() {
        return Messages.LEARNING_VIEW;
    }
}
