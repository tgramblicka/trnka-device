package com.trnka.trnkadevice.ui.evaluation;

import java.util.ArrayList;
import java.util.List;

import com.trnka.restapi.dto.statistics.Evaluate;
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
                                      Integer negativeTries,
                                      final Boolean isLastStep) {
        if (negativeTries == maxAllowedTries) {
            return new Evaluate(false,
                                negativeTries);
        }
        List<Keystroke> keystrokes = readInputKeystrokes();
        boolean isCorrect = step.getBrailCharacter().getBrailRepresentationKeystrokes().equals(keystrokes);
        if (isCorrect) {
            if (isLastStep){
                renderer.renderMessage(AudioMessage.of(Messages.CORRECT));
            } else {
                renderer.renderMessage(AudioMessage.of(Messages.LEARNING_CORRECT_CHARACTER_BRAIL_SEQUENCE_SUBMITTED));
            }

            return new Evaluate(true,
                                negativeTries);
        } else {
            negativeTries++;

            if (negativeTries == maxAllowedTries) {
               renderer.renderMessage(AudioMessage.of(Messages.LEARNING_INCORRECT_CHARACTER_BRAIL_SEQUENCE_SUBMITTED, step.getBrailCharacter().getBrailRepresentationAsMessages()));
            } else {
                renderer.renderMessage(AudioMessage.of(Messages.INCORRECT_GUESS));
            }
            renderer.renderMessage(AudioMessage.of(Messages.LEARNING_INCORRECT_CHARACTER_BRAIL_SEQUENCE_SUBMITTED_LEFT_RETRIES, Messages.fromNumber(maxAllowedTries - negativeTries)));

            return evaluateUserInput(step, maxAllowedTries, negativeTries, isLastStep);
        }
    }

    private List<Keystroke> readInputKeystrokes() {
        List<Keystroke> keyStrokes = new ArrayList<>();
        UserInteraction userInteraction = userInteractionHandler.readUserInteraction();
        while (!userInteraction.getKeystroke().equals(Keystroke.SUBMIT)) {
            log.info(userInteraction.getKeystroke().getValue());
            keyStrokes.add(userInteraction.getKeystroke());
            userInteraction = userInteractionHandler.readUserInteraction();
        }
        return keyStrokes;
    }

}
