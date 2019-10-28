package com.trnka.trnkadevice.ui.learning;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.domain.LearningSequence;
import com.trnka.trnkadevice.domain.Sequence;
import com.trnka.trnkadevice.domain.Step;
import com.trnka.trnkadevice.domain.statistics.SequenceStatistic;
import com.trnka.trnkadevice.domain.statistics.StepStatistic;
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
    private LearningSequenceComponent learningSequenceComponent;
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

    public void refresh(final LearningSequenceComponent learningSequenceComponent) {
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
        SequenceStatistic seqStats = createStatistic(seq);
        for (Step step : seq.getSteps()) {
            renderer.renderMessage(Messages.LEARNING_TYPE_IN_CHARACTER_BRAIL, step.getBrailCharacter().getLetter());
            long start = System.currentTimeMillis();
            Integer negativeRetries = 0;

            SequenceEvaluator evaluator = new SequenceEvaluator(renderer, inputReader);
            SequenceEvaluator.Evaluate evaluated = evaluator.evaluateUserInput(step, seq.getAllowedRetries(), negativeRetries);

            StepStatistic stepStats = new StepStatistic();
            stepStats.setStep(step);
            stepStats.setTook(System.currentTimeMillis() - start);
            stepStats.setCorrect(evaluated.getCorrect());
            stepStats.setRetries(evaluated.getNegativeTries());
            seqStats.getStepStats().add(stepStats);
        }

        renderer.renderMessage(Messages.LEARNING_SEQUENCE_END);
        sequenceStatisticRepository.save(seqStats);
        new StatisticRenderer().renderStatistic(renderer, seqStats);

        navigator.navigate(LearningSequenceSelectionView.class);
    }

    private SequenceStatistic createStatistic(final Sequence seq) {
        SequenceStatistic seqStats = new SequenceStatistic();
        seqStats.setSequence(seq);
        seqStats.setCreatedOn(new Date());
        return seqStats;
    }



    @Override
    public Messages getLabel() {
        return Messages.LEARNING_VIEW;
    }
}
