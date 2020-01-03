package com.trnka.trnkadevice.ui.learning;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.trnka.trnkadevice.dao.StatisticDao;
import com.trnka.trnkadevice.domain.LearningSequence;
import com.trnka.trnkadevice.domain.Step;
import com.trnka.trnkadevice.domain.statistics.SequenceStatistic;
import com.trnka.trnkadevice.inputreader.InputReader;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.repository.LearningSequenceRepository;
import com.trnka.trnkadevice.repository.SequenceStatisticRepository;
import com.trnka.trnkadevice.repository.UserRepository;
import com.trnka.trnkadevice.ui.IView;
import com.trnka.trnkadevice.ui.MenuStudentView;
import com.trnka.trnkadevice.ui.SequenceComponent;
import com.trnka.trnkadevice.ui.UserSession;
import com.trnka.trnkadevice.ui.evaluation.SequenceEvaluator;
import com.trnka.trnkadevice.ui.messages.Messages;
import com.trnka.trnkadevice.ui.navigation.Navigator;
import com.trnka.trnkadevice.ui.statistic.StatisticRenderer;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LearningView implements IView {

    private IRenderer renderer;
    private SequenceComponent<LearningSequence> learningSequenceComponent;
    private InputReader inputReader;
    private Navigator navigator;
    private StatisticDao statisticDao;

    private UserSession userSession;

    @Autowired
    public LearningView(final IRenderer renderer,
                        final InputReader inputReader,
                        final Navigator navigator,
                        final UserSession userSession,
                        final StatisticDao statisticDao) {
        this.renderer = renderer;
        this.inputReader = inputReader;
        this.navigator = navigator;
        this.userSession = userSession;
        this.statisticDao = statisticDao;
    }

    public void refresh(final SequenceComponent learningSequenceComponent) {
        this.learningSequenceComponent = learningSequenceComponent;
    }

    @Override
    @Transactional
    public void enter() {
        if (learningSequenceComponent == null) {
            log.error("Learning sequence component is null, this CANNOT HAPPEN");
            return;
        }
        this.renderer.renderLabel(learningSequenceComponent);

        LearningSequence seq = this.learningSequenceComponent.getSequence();

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
        statisticDao.saveSequenceStats(seqStats);
        renderStats(seqStats);
        navigator.navigate(MenuStudentView.class);
    }

    private void renderStats(final SequenceStatistic seqStats) {
        StatisticRenderer.renderStepsDetails(renderer, seqStats, seqStats.getSequence().getAllowedRetries());
    }

    @Override
    public Messages getLabel() {
        return Messages.LEARNING_VIEW;
    }

    @Override
    public List<String> getMessageParams() {
        return Collections.emptyList();
    }

}
