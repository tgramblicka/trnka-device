package com.trnka.trnkadevice.ui.evaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.sun.org.apache.regexp.internal.RE;
import com.trnka.trnkadevice.domain.Step;
import com.trnka.trnkadevice.inputreader.Keystroke;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.ui.interaction.UserInteraction;
import com.trnka.trnkadevice.ui.interaction.UserInteractionHandler;
import com.trnka.trnkadevice.ui.messages.AudioMessage;
import com.trnka.trnkadevice.ui.messages.Messages;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SequenceEvaluator {

    private final IRenderer renderer;
    private final UserInteractionHandler userInteractionHandler;

    public SequenceEvaluator(final IRenderer renderer,
                             final UserInteractionHandler userInteractionHandler) {
        this.renderer = renderer;
        this.userInteractionHandler = userInteractionHandler;
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
            renderer.renderMessage(AudioMessage.of(Messages.LEARNING_CORRECT_CHARACTER_BRAIL_SEQUENCE_SUBMITTED));
            return new Evaluate(true,
                                negativeTries);
        } else {
            negativeTries++;

            renderer.renderMessage(AudioMessage.of(Messages.LEARNING_INCORRECT_CHARACTER_BRAIL_SEQUENCE_SUBMITTED, step.getBrailCharacter().getBrailRepresentationAsMessages()));

            renderer.renderMessage(AudioMessage.of(Messages.LEARNING_INCORRECT_CHARACTER_BRAIL_SEQUENCE_SUBMITTED_LEFT_RETRIES, Messages.fromNumber(maxAllowedTries - negativeTries)));

            return evaluateUserInput(step, maxAllowedTries, negativeTries);
        }
    }

    private List<Keystroke> readInputKeystrokes() {
        List<Keystroke> keyStrokes = new ArrayList<>();
        UserInteraction userInteraction = userInteractionHandler.readUserInteraction();
        while (!userInteraction.getKeystroke().equals(Keystroke.SUBMIT) && !userInteraction.isFlowBreakingCondition()) {
            log.info(userInteraction.getKeystroke().getValue());
            keyStrokes.add(userInteraction.getKeystroke());
            userInteraction = userInteractionHandler.readUserInteraction();
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
