package com.trnka.trnkadevice.ui.testing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.domain.Step;
import com.trnka.trnkadevice.domain.TestingSequence;
import com.trnka.trnkadevice.domain.statistics.SequenceStatistic;
import com.trnka.trnkadevice.inputreader.InputReader;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.repository.SequenceStatisticRepository;
import com.trnka.trnkadevice.ui.IView;
import com.trnka.trnkadevice.ui.evaluation.SequenceEvaluator;
import com.trnka.trnkadevice.ui.learning.LearningSequenceSelectionView;
import com.trnka.trnkadevice.ui.SequenceComponent;
import com.trnka.trnkadevice.ui.messages.Messages;
import com.trnka.trnkadevice.ui.navigation.Navigator;
import com.trnka.trnkadevice.ui.statistic.StatisticRenderer;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TestingView implements IView {
    private SequenceComponent<TestingSequence> testingSequenceComponent;

    private IRenderer renderer;
    private Navigator navigator;
    private InputReader inputReader;
    private SequenceStatisticRepository sequenceStatisticRepository;

    @Autowired
    public TestingView(final IRenderer renderer,
                       final Navigator navigator,
                       final InputReader inputReader,
                       final SequenceStatisticRepository sequenceStatisticRepository) {
        this.renderer = renderer;
        this.navigator = navigator;
        this.inputReader = inputReader;
        this.sequenceStatisticRepository = sequenceStatisticRepository;
    }

    public void refresh(final SequenceComponent testingSequenceComponent) {
        this.testingSequenceComponent = testingSequenceComponent;
    }

    @Override
    public void enter() {
        if (testingSequenceComponent == null) {
            log.error("Learning sequence component is null, this CANNOT HAPPEN");
            return;
        }
        this.renderer.renderLabel(testingSequenceComponent);

        TestingSequence seq = this.testingSequenceComponent.getSequence();
        SequenceStatistic seqStats = SequenceStatistic.create(seq);
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

        renderer.renderMessage(Messages.TESTING_SEQUENCE_END);
        sequenceStatisticRepository.save(seqStats);
        new StatisticRenderer().renderStatisticForTest(renderer, seqStats);

        navigator.navigate(LearningSequenceSelectionView.class);
    }

    @Override
    public Messages getLabel() {
        return Messages.TESTING_LABEL;
    }
}
