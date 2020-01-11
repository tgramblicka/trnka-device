package com.trnka.trnkadevice.ui.learning;

import java.util.Collections;
import java.util.List;

import com.trnka.trnkadevice.domain.MethodicalLearningSequence;
import com.trnka.trnkadevice.ui.testing.MethodicalTestingView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.trnka.trnkadevice.service.StatisticService;
import com.trnka.trnkadevice.domain.Step;
import com.trnka.trnkadevice.domain.statistics.SequenceStatistic;
import com.trnka.trnkadevice.inputreader.InputReader;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.ui.IView;
import com.trnka.trnkadevice.ui.SequenceComponent;
import com.trnka.trnkadevice.ui.UserSession;
import com.trnka.trnkadevice.ui.evaluation.SequenceEvaluator;
import com.trnka.trnkadevice.ui.messages.Messages;
import com.trnka.trnkadevice.ui.navigation.Navigator;
import com.trnka.trnkadevice.ui.statistic.StatisticRenderer;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MethodicalLearningView implements IView {

    private IRenderer renderer;
    private SequenceComponent<MethodicalLearningSequence> learningSequenceComponent;
    private InputReader inputReader;
    private Navigator navigator;
    private StatisticService statisticService;
    private MethodicalTestingView methodicalTestingView;

    private UserSession userSession;

    @Autowired
    public MethodicalLearningView(final IRenderer renderer,
                                  final InputReader inputReader,
                                  final Navigator navigator,
                                  final UserSession userSession,
                                  final StatisticService statisticService,
                                  final MethodicalTestingView methodicalTestingView) {
        this.renderer = renderer;
        this.inputReader = inputReader;
        this.navigator = navigator;
        this.userSession = userSession;
        this.statisticService = statisticService;
        this.methodicalTestingView = methodicalTestingView;
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

        MethodicalLearningSequence seq = this.learningSequenceComponent.getSequence();

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
