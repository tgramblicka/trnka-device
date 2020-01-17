package com.trnka.trnkadevice.ui.testing;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.trnka.trnkadevice.service.StatisticService;
import com.trnka.trnkadevice.domain.Step;
import com.trnka.trnkadevice.domain.TestingSequence;
import com.trnka.trnkadevice.domain.statistics.SequenceStatistic;
import com.trnka.trnkadevice.inputreader.InputReader;
import com.trnka.trnkadevice.renderer.IRenderer;
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
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class IndividualTestingView implements IView {
    private SequenceComponent<TestingSequence> testingSequenceComponent;

    private IRenderer renderer;
    private Navigator navigator;
    private InputReader inputReader;
    private UserSession userSession;
    private StatisticService statisticService;

    @Autowired
    public IndividualTestingView(final IRenderer renderer,
                                 final Navigator navigator,
                                 final InputReader inputReader,
                                 final UserSession userSession,
                                 final StatisticService statisticService) {
        this.renderer = renderer;
        this.navigator = navigator;
        this.inputReader = inputReader;
        this.userSession = userSession;
        this.statisticService = statisticService;
    }

    public void refresh(final SequenceComponent testingSequenceComponent) {
        this.testingSequenceComponent = testingSequenceComponent;
    }

    @Override
    @Transactional
    public void enter() {
        if (testingSequenceComponent == null) {
            log.error("Testing sequence component is null, this CANNOT HAPPEN");
            return;
        }
        this.renderer.renderLabel(testingSequenceComponent);

        TestingSequence seq = this.testingSequenceComponent.getSequence();
        SequenceStatistic seqStats = SequenceStatistic.create(seq, userSession.getUser().get());
        for (Step step : seq.getSteps()) {
            renderer.renderMessage(Messages.TESTING_TYPE_IN_CHARACTER_BRAIL, step.getBrailCharacter().getLetter());
            long start = System.currentTimeMillis();
            Integer negativeRetries = 0;
            SequenceEvaluator evaluator = new SequenceEvaluator(renderer,
                                                                inputReader);
            SequenceEvaluator.Evaluate evaluated = evaluator.evaluateUserInput(step, seq.getAllowedRetries(), negativeRetries);
            long took = System.currentTimeMillis() - start;
            seqStats.addStepStatistic(seqStats, step, took, evaluated);
        }
        statisticService.saveSequenceStats(seqStats);

        renderStats(seqStats);
        renderer.renderMessage(Messages.TESTING_SEQUENCE_END);

        navigator.navigateAsync(MenuStudentView.class);
    }

    private void renderStats(final SequenceStatistic seqStats) {
        StatisticRenderer.renderStatisticForTest(renderer, seqStats, seqStats.getSequence().getAllowedRetries());
    }

    @Override
    public Messages getLabel() {
        return Messages.TESTING_LABEL_MENU;
    }

    @Override
    public List<String> getMessageParams() {
        return Collections.emptyList();
    }

}
