package com.trnka.trnkadevice.ui.learning;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
public class LettersLearningView implements IView {

    private IRenderer renderer;
    private UserInteractionHandler userInteractionHandler;
    private Navigator navigator;
    private LettersTestingView lettersTestingView;
    private MethodicalLearningSequenceRepository repo;

    private Long sequenceId;

    @Autowired
    public LettersLearningView(final IRenderer renderer,
                               final UserInteractionHandler userInteractionHandler,
                               final Navigator navigator,
                               final MethodicalLearningSequenceRepository repo,
                               final LettersTestingView lettersTestingView) {
        this.renderer = renderer;
        this.userInteractionHandler = userInteractionHandler;
        this.navigator = navigator;
        this.repo = repo;
        this.lettersTestingView = lettersTestingView;
    }

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
        this.renderer.renderMessage(seq.getAudioMessage());

        for (Step step : seq.getSteps()) {
            renderer.renderMessage(Messages.LEARNING_TYPE_IN_CHARACTER_BRAIL, step.getBrailCharacter().getLetter(), step.getBrailCharacter().getBrailRepresentationAsString());
            Integer negativeRetries = 0;

            SequenceEvaluator evaluator = new SequenceEvaluator(renderer,
                                                                userInteractionHandler);
            evaluator.evaluateUserInput(step, seq.getAllowedRetries(), negativeRetries);
        }
        renderer.renderMessage(Messages.LEARNING_SEQUENCE_END);
        lettersTestingView.refresh(seq.getId());
        navigator.navigateAsync(lettersTestingView.getClass());
    }

    @Override
    public Messages getLabel() {
        return null;
    }

    @Override
    public List<String> getMessageParams() {
        return Collections.emptyList();
    }

}
