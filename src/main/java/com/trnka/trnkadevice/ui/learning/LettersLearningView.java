package com.trnka.trnkadevice.ui.learning;

import java.util.Collections;
import java.util.List;

import com.trnka.trnkadevice.ui.YesOrNoView;
import com.trnka.trnkadevice.ui.messages.AudioMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.trnka.trnkadevice.domain.MethodicalLearningSequence;
import com.trnka.trnkadevice.domain.Step;
import com.trnka.trnkadevice.exception.SequenceIdNotSetException;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.repository.MethodicalLearningSequenceRepository;
import com.trnka.trnkadevice.ui.IView;
import com.trnka.trnkadevice.ui.evaluation.SequenceEvaluator;
import com.trnka.trnkadevice.ui.interaction.UserInteractionHandler;
import com.trnka.trnkadevice.ui.messages.Messages;
import com.trnka.trnkadevice.ui.navigation.Navigator;
import com.trnka.trnkadevice.ui.testing.LettersTestingView;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequiredArgsConstructor
public class LettersLearningView implements IView {

    private final IRenderer renderer;
    private final UserInteractionHandler userInteractionHandler;
    private final Navigator navigator;
    private final LettersTestingView lettersTestingView;
    private final MethodicalLearningSequenceRepository repo;
    private final YesOrNoView yesOrNoView;

    private Long sequenceId;

    public void refresh(final Long sequenceId) {
        this.sequenceId = sequenceId;
    }

    @Override
    @Transactional
    public void enter() {
        if (sequenceId == null) {
            log.error("Sequence id is null!");
            throw new SequenceIdNotSetException("Sequence ID is null on entering the View!");
        }

        MethodicalLearningSequence seq = repo.findById(sequenceId).get();
        this.renderer.renderMessage(AudioMessage.of(Messages.SEQUENCE, seq.getAudioMessage()));

        int index = -1;
        for (Step step : seq.getSteps()) {
            index++;
            AudioMessage audioMessage1 = AudioMessage.of(Messages.LEARNING_TYPE_IN_CHARACTER_BRAIL, step.getBrailCharacter().getLetterMessage());
            renderer.renderMessage(audioMessage1);

            renderer.renderMessages(step.getBrailCharacter().getBrailRepresentationAsMessages());
            Integer negativeRetries = 0;

            SequenceEvaluator evaluator = new SequenceEvaluator(renderer,
                                                                userInteractionHandler);

            evaluator.evaluateUserInput(step, seq.getAllowedRetries(), negativeRetries, index + 1 == seq.getSteps().size());
        }
        renderer.renderMessage(AudioMessage.of(Messages.LEARNING_SEQUENCE_END, seq.getAllStepsAsMessagesList()));

        yesOrNoView.refresh(yesSelected -> handleYesNoSelection(yesSelected, seq.getId()), AudioMessage.of(Messages.DO_YOU_WANT_TO_START_TEST));
        navigator.navigateAsync(YesOrNoView.class);
    }


    private void handleYesNoSelection(Boolean yesSelected, Long sequenceId) {
        if (yesSelected) {
            lettersTestingView.refresh(sequenceId);
            navigator.navigateAsync(lettersTestingView.getClass());
        } else {
            navigator.navigateAsync(LettersSelectionView.class);
        }
    }

    @Override
    public Class<? extends IView> onEscape() {
        return MethodicalLearningMenuView.class;
    }

    @Override
    public Messages getMessage() {
        return null;
    }

    @Override
    public List<Messages> getParams() {
        return Collections.emptyList();
    }

}
