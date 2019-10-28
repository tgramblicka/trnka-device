package com.trnka.trnkadevice.ui.learning;

import com.trnka.trnkadevice.ui.SequenceComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.domain.LearningSequence;
import com.trnka.trnkadevice.domain.Step;
import com.trnka.trnkadevice.domain.statistics.SequenceStatistic;
import com.trnka.trnkadevice.inputreader.InputReader;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.repository.SequenceStatisticRepository;
import com.trnka.trnkadevice.ui.IView;
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
    private SequenceStatisticRepository sequenceStatisticRepository;

    @Autowired
    public LearningView(final IRenderer renderer,
                        final InputReader inputReader,
                        final Navigator navigator,
                        final SequenceStatisticRepository sequenceStatisticRepository) {
        this.renderer = renderer;
        this.inputReader = inputReader;
        this.navigator = navigator;
        this.sequenceStatisticRepository = sequenceStatisticRepository;
    }

    public void refresh(final SequenceComponent learningSequenceComponent) {
        this.learningSequenceComponent = learningSequenceComponent;
    }

    @Override
    public void enter() {
        if (learningSequenceComponent == null) {
            log.error("Learning sequence component is null, this CANNOT HAPPEN");
            return;
        }
        this.renderer.renderLabel(learningSequenceComponent);

        LearningSequence seq = this.learningSequenceComponent.getSequence();
        SequenceStatistic seqStats = SequenceStatistic.create(seq);
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
        sequenceStatisticRepository.save(seqStats);
        new StatisticRenderer().renderStatistic(renderer, seqStats);

        navigator.navigate(LearningSequenceSelectionView.class);
    }

    @Override
    public Messages getLabel() {
        return Messages.LEARNING_VIEW;
    }
}
