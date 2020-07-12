package com.trnka.trnkadevice.ui.testing;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;

import com.trnka.trnkadevice.ui.messages.AudioMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
public class LettersTestingView implements IView {

    private IRenderer renderer;
    private Navigator navigator;
    private UserInteractionHandler userInteractionHandler;
    private UserSession userSession;
    private UserRepository userRepo;
    private StatisticService statisticService;
    private MethodicalLearningSequenceRepository methodicalLearningSequenceRepository;

    private Long sequenceId;

    @Autowired
    public LettersTestingView(final IRenderer renderer,
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

        this.renderer.renderMessage(AudioMessage.of(Messages.METHODICAL_LEARNING_TEST_ENTERED));
        MethodicalLearningSequence sequence = methodicalLearningSequenceRepository.findById(sequenceId)
                .orElseThrow(() -> new NoResultException("Sequence was not found!"));

        User user = userSession.getUser().get();
        SequenceStatistic seqStats = SequenceStatistic.create(sequence, user);
        int index = -1;
        for (Step step : sequence.getSteps()) {

            renderer.renderMessage(AudioMessage.of(Messages.TESTING_TYPE_IN_CHARACTER_BRAIL, step.getBrailCharacter().getLetterMessage()));
            long start = System.currentTimeMillis();
            Integer negativeRetries = 0;
            SequenceEvaluator evaluator = new SequenceEvaluator(renderer,
                                                                userInteractionHandler);
            SequenceEvaluator.Evaluate evaluated = evaluator.evaluateUserInput(step, sequence.getAllowedTestRetries(), negativeRetries, index+1==sequence.getSteps().size());
            long took = System.currentTimeMillis() - start;
            seqStats.addStepStatistic(seqStats, step, took, evaluated);
        }
        statisticService.saveMethodicalLearingTestStats(seqStats);
        if (seqStats.getScore().multiply(BigDecimal.valueOf(100.0D)).compareTo(sequence.getPassingRate()) > 0) {
            renderer.renderMessage(AudioMessage.of(Messages.METHODICAL_LEARNING_TEST_PASSED));
            user.addPassedMethodic(sequence);
            userRepo.save(user);
        } else {
            renderer.renderMessage(AudioMessage.of(Messages.METHODICAL_LEARNING_TEST_NOT_PASSED));
        }
        renderStats(seqStats);
        renderer.renderMessage(AudioMessage.of(Messages.METHODICAL_LEARNING_ENDED));
        navigator.navigateAsync(MenuStudentView.class);
    }

    private void renderStats(final SequenceStatistic seqStats) {
        StatisticRenderer.renderStatisticForTest(renderer, seqStats, seqStats.getSequence().getAllowedRetries());
    }

    @Override
    public Messages getMessage() {
        return Messages.TESTING_LETTERS_LABEL_MENU;
    }

    @Override
public List<Messages> getParams() {
        return Collections.emptyList();
    }

}
