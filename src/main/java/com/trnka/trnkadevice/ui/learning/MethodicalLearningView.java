package com.trnka.trnkadevice.ui.learning;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.trnka.trnkadevice.TransactionalUtil;
import com.trnka.trnkadevice.exception.SequenceIdNotSetException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.trnka.trnkadevice.domain.MethodicalLearningSequence;
import com.trnka.trnkadevice.domain.Step;
import com.trnka.trnkadevice.inputreader.InputReader;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.repository.MethodicalLearningSequenceRepository;
import com.trnka.trnkadevice.service.StatisticService;
import com.trnka.trnkadevice.ui.IView;
import com.trnka.trnkadevice.ui.UserSession;
import com.trnka.trnkadevice.ui.evaluation.SequenceEvaluator;
import com.trnka.trnkadevice.ui.messages.Messages;
import com.trnka.trnkadevice.ui.navigation.Navigator;
import com.trnka.trnkadevice.ui.testing.MethodicalTestingView;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Transactional(propagation =  Propagation.REQUIRES_NEW)
public class MethodicalLearningView implements IView {

    private IRenderer renderer;
    private InputReader inputReader;
    private Navigator navigator;
    private StatisticService statisticService;
    private MethodicalTestingView methodicalTestingView;
    private MethodicalLearningSequenceRepository repo;
    private UserSession userSession;

    private Long sequenceId;

    @Autowired
    private TransactionalUtil transactionalUtil;

    @Autowired
    public MethodicalLearningView(final IRenderer renderer,
                                  final InputReader inputReader,
                                  final Navigator navigator,
                                  final UserSession userSession,
                                  final StatisticService statisticService,
                                  final MethodicalLearningSequenceRepository repo,
                                  final MethodicalTestingView methodicalTestingView) {
        this.renderer = renderer;
        this.inputReader = inputReader;
        this.navigator = navigator;
        this.userSession = userSession;
        this.statisticService = statisticService;
        this.repo = repo;
        this.methodicalTestingView = methodicalTestingView;
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
            renderer.renderMessage(Messages.LEARNING_TYPE_IN_CHARACTER_BRAIL, step.getBrailCharacter().getLetter());
            Integer negativeRetries = 0;

            SequenceEvaluator evaluator = new SequenceEvaluator(renderer,
                                                                inputReader);
            evaluator.evaluateUserInput(step, seq.getAllowedRetries(), negativeRetries);
        }
        renderer.renderMessage(Messages.LEARNING_SEQUENCE_END);
        methodicalTestingView.refresh(seq.getId());
        navigator.navigate(methodicalTestingView);
    }

    @Override
    public Messages getLabel() {
        return Messages.METHODICAL_LEARNING_VIEW;
    }

    @Override
    public List<String> getMessageParams() {
        return Collections.emptyList();
    }

}
