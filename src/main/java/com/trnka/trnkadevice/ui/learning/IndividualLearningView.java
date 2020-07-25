package com.trnka.trnkadevice.ui.learning;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.trnka.trnkadevice.domain.LearningSequence;
import com.trnka.trnkadevice.domain.Step;
import com.trnka.trnkadevice.domain.statistics.SequenceStatistic;
import com.trnka.trnkadevice.exception.SequenceIdNotSetException;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.repository.LearningSequenceRepository;
import com.trnka.trnkadevice.service.StatisticService;
import com.trnka.trnkadevice.ui.IView;
import com.trnka.trnkadevice.ui.MenuStudentView;
import com.trnka.trnkadevice.ui.UserSession;
import com.trnka.trnkadevice.ui.evaluation.SequenceEvaluator;
import com.trnka.trnkadevice.ui.interaction.UserInteractionHandler;
import com.trnka.trnkadevice.ui.messages.AudioMessage;
import com.trnka.trnkadevice.ui.messages.Messages;
import com.trnka.trnkadevice.ui.navigation.Navigator;
import com.trnka.trnkadevice.ui.statistic.StatisticRenderer;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class IndividualLearningView implements IView {

    private final LearningSequenceRepository repository;
    private IRenderer renderer;
    private UserInteractionHandler userInteractionHandler;
    private Navigator navigator;
    private StatisticService statisticService;

    private UserSession userSession;
    private Long sequenceId;

    @Autowired
    public IndividualLearningView(final IRenderer renderer,
                                  final UserInteractionHandler userInteractionHandler,
                                  final Navigator navigator,
                                  final UserSession userSession,
                                  final StatisticService statisticService,
                                  final LearningSequenceRepository repository) {
        this.renderer = renderer;
        this.userInteractionHandler = userInteractionHandler;
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
        SequenceStatistic seqStats = SequenceStatistic.create(seq, userSession.getUser().get());
        int index = -1;
        for (Step step : seq.getSteps()) {
            index++;
            AudioMessage audioMessage1 = AudioMessage.of(Messages.LEARNING_TYPE_IN_CHARACTER_BRAIL, step.getBrailCharacter().getLetterMessage());
            renderer.renderMessage(audioMessage1);
            renderer.renderMessages(step.getBrailCharacter().getBrailRepresentationAsMessages());

            long start = System.currentTimeMillis();
            Integer negativeRetries = 0;

            SequenceEvaluator evaluator = new SequenceEvaluator(renderer,
                                                                userInteractionHandler);
            long took = System.currentTimeMillis() - start;
            SequenceEvaluator.Evaluate evaluated = evaluator.evaluateUserInput(step, seq.getAllowedRetries(), negativeRetries, index+1 == seq.getSteps().size());
            seqStats.addStepStatistic(seqStats, step, took, evaluated);
        }

        renderer.renderMessage(AudioMessage.of(Messages.LEARNING_SEQUENCE_END, seq.getAllStepsAsMessagesList()));
        statisticService.saveSequenceStats(seqStats);
        renderStats(seqStats);
        navigator.navigateAsync(MenuStudentView.class);
    }

    @Override
    public Class<? extends IView> onEscape() {
        return IndividualLearningView.class;
    }

    private void renderStats(final SequenceStatistic seqStats) {
        StatisticRenderer.renderStatisticForTest(renderer, seqStats, seqStats.getSequence().getAllowedRetries());
    }

    @Override
    public Messages getMessage() {
        return Messages.LEARNING_VIEW_MENU;
    }

    @Override
    public List<Messages> getParams() {
        return Collections.emptyList();
    }

}
