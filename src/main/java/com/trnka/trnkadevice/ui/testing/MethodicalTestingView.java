package com.trnka.trnkadevice.ui.testing;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.trnka.trnkadevice.TransactionalUtil;
import com.trnka.trnkadevice.domain.MethodicalLearningSequence;
import com.trnka.trnkadevice.domain.Step;
import com.trnka.trnkadevice.domain.User;
import com.trnka.trnkadevice.domain.statistics.SequenceStatistic;
import com.trnka.trnkadevice.exception.SequenceIdNotSetException;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.repository.MethodicalLearningSequenceRepository;
import com.trnka.trnkadevice.repository.UserRepository;
import com.trnka.trnkadevice.service.StatisticService;
import com.trnka.trnkadevice.ui.IView;
import com.trnka.trnkadevice.ui.MenuStudentView;
import com.trnka.trnkadevice.ui.UserSession;
import com.trnka.trnkadevice.ui.evaluation.SequenceEvaluator;
import com.trnka.trnkadevice.ui.interaction.UserInteractionHandler;
import com.trnka.trnkadevice.ui.messages.Messages;
import com.trnka.trnkadevice.ui.navigation.Navigator;
import com.trnka.trnkadevice.ui.statistic.StatisticRenderer;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class MethodicalTestingView implements IView {

    private IRenderer renderer;
    private Navigator navigator;
    private UserInteractionHandler userInteractionHandler;
    private UserSession userSession;
    private UserRepository userRepo;
    private StatisticService statisticService;
    private MethodicalLearningSequenceRepository methodicalLearningSequenceRepository;

    private Long sequenceId;
    @Autowired
    private TransactionalUtil transactionalUtil;

    @Autowired
    public MethodicalTestingView(final IRenderer renderer,
                                 final Navigator navigator,
                                 final UserInteractionHandler userInteractionHandler,
                                 final UserSession userSession,
                                 final UserRepository userRepo,
                                 final StatisticService statisticService,
                                 final MethodicalLearningSequenceRepository methodicalLearningSequenceRepository) {
        this.renderer = renderer;
        this.navigator = navigator;
        this.userInteractionHandler = userInteractionHandler;
        this.userSession = userSession;
        this.userRepo = userRepo;
        this.statisticService = statisticService;
        this.methodicalLearningSequenceRepository = methodicalLearningSequenceRepository;
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

        this.renderer.renderMessage(Messages.METHODICAL_LEARNING_TEST_ENTERED);
        MethodicalLearningSequence sequence = methodicalLearningSequenceRepository.findById(sequenceId)
                .orElseThrow(() -> new NoResultException("Sequence was not found!"));

        User user = userSession.getUser().get();
        SequenceStatistic seqStats = SequenceStatistic.create(sequence, user);
        for (Step step : sequence.getSteps()) {
            renderer.renderMessage(Messages.TESTING_TYPE_IN_CHARACTER_BRAIL, step.getBrailCharacter().getLetter());
            long start = System.currentTimeMillis();
            Integer negativeRetries = 0;
            SequenceEvaluator evaluator = new SequenceEvaluator(renderer,
                                                                userInteractionHandler);
            SequenceEvaluator.Evaluate evaluated = evaluator.evaluateUserInput(step, sequence.getAllowedTestRetries(), negativeRetries);
            long took = System.currentTimeMillis() - start;
            seqStats.addStepStatistic(seqStats, step, took, evaluated);
        }
        statisticService.saveMethodicalLearingTestStats(seqStats);
        if (seqStats.getScore().multiply(BigDecimal.valueOf(100.0D)).compareTo(sequence.getPassingRate()) > 0) {
            renderer.renderMessage(Messages.METHODICAL_LEARNING_TEST_PASSED);
            user.addPassedMethodic(sequence);
            userRepo.save(user);
        } else {
            renderer.renderMessage(Messages.METHODICAL_LEARNING_TEST_NOT_PASSED);
        }
        renderStats(seqStats);
        renderer.renderMessage(Messages.METHODICAL_LEARNING_ENDED);
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
