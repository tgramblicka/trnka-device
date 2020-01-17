package com.trnka.trnkadevice.ui.evaluation;

import com.trnka.trnkadevice.domain.Step;
import com.trnka.trnkadevice.inputreader.InputReader;
import com.trnka.trnkadevice.inputreader.Keystroke;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.ui.messages.Messages;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class SequenceEvaluator {

    private final IRenderer renderer;
    private final InputReader inputReader;

    public SequenceEvaluator(final IRenderer renderer,
                             final InputReader inputReader) {
        this.renderer = renderer;
        this.inputReader = inputReader;
    }

    public Evaluate evaluateUserInput(Step step,
                                      int maxAllowedTries,
                                      Integer negativeTries) {
        if (negativeTries == maxAllowedTries) {
            return new Evaluate(false,
                                negativeTries);
        }
        List<Keystroke> keystrokes = readInputKeystrokes();
        boolean isCorrect = step.getBrailCharacter().getBrailRepresentationKeystrokes().equals(keystrokes);
        if (isCorrect) {
            renderer.renderMessage(Messages.LEARNING_CORRECT_CHARACTER_BRAIL_SEQUENCE_SUBMITTED);
            return new Evaluate(true,
                                negativeTries);
        } else {
            negativeTries++;
            renderer.renderMessage(Messages.LEARNING_INCORRECT_CHARACTER_BRAIL_SEQUENCE_SUBMITTED,
                    step.getBrailCharacter().getBrailRepresentation().stream().map(String::valueOf).collect(Collectors.joining(",")),
                    String.valueOf(maxAllowedTries - negativeTries));
            return evaluateUserInput(step, maxAllowedTries, negativeTries);
        }
    }

    private List<Keystroke> readInputKeystrokes() {
        List<Keystroke> keyStrokes = new ArrayList<>();
        Keystroke keystroke = inputReader.readFromInput();

        while (!keystroke.equals(Keystroke.SUBMIT) && keystroke.equals(Keystroke.MENU_1)) {
            log.info(keystroke.getValue());
            keyStrokes.add(keystroke);
            keystroke = inputReader.readFromInput();
        }
        return keyStrokes;
    }

    public class Evaluate {
        private final Boolean correct;
        private final Integer negativeTries;

        public Evaluate(Boolean correct,
                        Integer negativeTries) {
            this.correct = correct;
            this.negativeTries = negativeTries;
        }

        public Boolean getCorrect() {
            return correct;
        }

        public Integer getNegativeTries() {
            return negativeTries;
        }

    }

}
