package com.trnka.trnkadevice.ui.learning;

import java.util.ArrayList;
import java.util.List;

import com.trnka.trnkadevice.domain.SequenceStep;
import com.trnka.trnkadevice.domain.statistics.SequenceStatistic;
import com.trnka.trnkadevice.domain.statistics.StepStatistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.inputreader.InputReader;
import com.trnka.trnkadevice.inputreader.Keystroke;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.ui.IView;
import com.trnka.trnkadevice.ui.messages.Messages;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LearningView implements IView {

    private IRenderer renderer;
    private LearningSequenceComponent learningSequenceComponent;
    private InputReader inputReader;

    @Autowired
    public LearningView(final IRenderer renderer,
                        final InputReader inputReader) {
        this.renderer = renderer;
        this.inputReader = inputReader;
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

        SequenceStatistic seqStats = new SequenceStatistic();
        for (SequenceStep step : this.learningSequenceComponent.getSequence().getSteps()) {
            StepStatistic stepStats = new StepStatistic();
            seqStats.getStepStats().add(stepStats);
            renderer.renderMessage(step.getBrailCharacter());
            long start = System.currentTimeMillis();
            evaluateUserInput(step, stepStats, learningSequenceComponent.getSequence().getAllowedRetries(), 0);
            stepStats.setTook(System.currentTimeMillis() - start);
        }

    }

    private List<Keystroke> readInputKeystrokes() {
        List<Keystroke> keyStrokes = new ArrayList<>();
        Keystroke keystroke = inputReader.readFromInput();

        while (!keystroke.equals(Keystroke.SUBMIT)) {
            keyStrokes.add(keystroke);
            keystroke = inputReader.readFromInput();
        }
        return keyStrokes;
    }

    private boolean evaluateUserInput(
            SequenceStep step, StepStatistic stepStats, int maxAllowedTries, int negativeTries) {
        if (negativeTries == maxAllowedTries) {
            renderer.renderMessage(Messages.LEARNING_MAXIMUM_NEGATIVE_TRIES_REACHED);
            return false;
        }
        List<Keystroke> keystrokes = readInputKeystrokes();
        boolean isCorrect = step.getBrailCharacter().getBrailRepresentation().equals(keystrokes);
        if (isCorrect) {
            renderer.renderMessage(Messages.LEARNING_CORRECT_CHARACTER_BRAIL_SEQUENCE_SUBMITTED);
            return true;
        } else {
            negativeTries++;
            stepStats.setRetries(negativeTries);
            renderer.renderMessage(Messages.LEARNING_INCORRECT_CHARACTER_BRAIL_SEQUENCE_SUBMITTED, String.valueOf(maxAllowedTries - negativeTries));
            return evaluateUserInput(step, stepStats, maxAllowedTries, negativeTries);
        }
    }

    @Override
    public Messages getLabel() {
        return Messages.LEARNING_VIEW;
    }
}
