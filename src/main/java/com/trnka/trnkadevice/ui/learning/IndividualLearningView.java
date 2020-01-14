package com.trnka.trnkadevice.ui.learning;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.trnka.trnkadevice.domain.LearningSequence;
import com.trnka.trnkadevice.domain.Step;
import com.trnka.trnkadevice.domain.statistics.SequenceStatistic;
import com.trnka.trnkadevice.exception.SequenceIdNotSetException;
import com.trnka.trnkadevice.inputreader.InputReader;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.repository.LearningSequenceRepository;
import com.trnka.trnkadevice.service.StatisticService;
import com.trnka.trnkadevice.ui.IView;
import com.trnka.trnkadevice.ui.MenuStudentView;
import com.trnka.trnkadevice.ui.UserSession;
import com.trnka.trnkadevice.ui.evaluation.SequenceEvaluator;
import com.trnka.trnkadevice.ui.messages.Messages;
import com.trnka.trnkadevice.ui.navigation.Navigator;
import com.trnka.trnkadevice.ui.statistic.StatisticRenderer;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class IndividualLearningView implements IView {

    private final LearningSequenceRepository repository;
    private IRenderer renderer;
    private InputReader inputReader;
    private Navigator navigator;
    private StatisticService statisticService;

    private UserSession userSession;
    private Long sequenceId;

    @Autowired
    public IndividualLearningView(final IRenderer renderer,
                                  final InputReader inputReader,
                                  final Navigator navigator,
                                  final UserSession userSession,
                                  final StatisticService statisticService,
                                  final LearningSequenceRepository repository) {
        this.renderer = renderer;
        this.inputReader = inputReader;
        this.navigator = navigator;
        this.userSession = userSession;
        this.statisticService = statisticService;
        this.repository = repository;
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
        LearningSequence seq = repository.findById(sequenceId).get();
        this.renderer.renderMessage(seq.getAudioMessage());
        SequenceStatistic seqStats = SequenceStatistic.create(seq, userSession.getUser().get());
        for (Step step : seq.getSteps()) {
            renderer.renderMessage(Messages.LEARNING_TYPE_IN_CHARACTER_BRAIL, step.getBrailCharacter().getLetter());
            long start = System.currentTimeMillis();
            Integer negativeRetries = 0;

            SequenceEvaluator evaluator = new SequenceEvaluator(renderer,
                                                                inputReader);
            long took = System.currentTimeMillis() - start;
            SequenceEvaluator.Evaluate evaluated = evaluator.evaluateUserInput(step, seq.getAllowedRetries(), negativeRetries);
            seqStats.addStepStatistic(seqStats, step, took, evaluated);
        }

        renderer.renderMessage(Messages.LEARNING_SEQUENCE_END);
        statisticService.saveSequenceStats(seqStats);
        renderStats(seqStats);
        navigator.navigateAsync(MenuStudentView.class);
    }

    private void renderStats(final SequenceStatistic seqStats) {
        StatisticRenderer.renderStepsDetails(renderer, seqStats, seqStats.getSequence().getAllowedRetries());
    }

    @Override
    public Messages getLabel() {
        return Messages.LEARNING_VIEW_MENU;
    }

    @Override
    public List<String> getMessageParams() {
        return Collections.emptyList();
    }

}
