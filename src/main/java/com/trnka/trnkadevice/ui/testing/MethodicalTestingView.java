package com.trnka.trnkadevice.ui.testing;

import java.util.Collections;
import java.util.List;

import com.trnka.trnkadevice.TransactionalUtil;
import com.trnka.trnkadevice.domain.MethodicalLearningSequence;
import com.trnka.trnkadevice.domain.User;
import com.trnka.trnkadevice.exception.SequenceIdNotSetException;
import com.trnka.trnkadevice.repository.MethodicalLearningSequenceRepository;
import com.trnka.trnkadevice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.trnka.trnkadevice.service.StatisticService;
import com.trnka.trnkadevice.domain.Step;
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

import javax.persistence.NoResultException;

@Component
@Slf4j
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class MethodicalTestingView implements IView {
    private SequenceComponent<MethodicalLearningSequence> sequenceComponent;

    private IRenderer renderer;
    private Navigator navigator;
    private InputReader inputReader;
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
                                 final InputReader inputReader,
                                 final UserSession userSession,
                                 final UserRepository userRepo,
                                 final StatisticService statisticService,
                                 final MethodicalLearningSequenceRepository methodicalLearningSequenceRepository) {
        this.renderer = renderer;
        this.navigator = navigator;
        this.inputReader = inputReader;
        this.userSession = userSession;
        this.userRepo = userRepo;
        this.statisticService = statisticService;
        this.methodicalLearningSequenceRepository = methodicalLearningSequenceRepository;
    }

    public void refresh(final Long sequenceId) {
        this.sequenceId = sequenceId;
    }

    @Override
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
                                                                inputReader);
            SequenceEvaluator.Evaluate evaluated = evaluator.evaluateUserInput(step, sequence.getAllowedTestRetries(), negativeRetries);
            long took = System.currentTimeMillis() - start;
            seqStats.addStepStatistic(seqStats, step, took, evaluated);
        }
        statisticService.saveMethodicalLearingTestStats(seqStats);
        if (sequence.getPassingRate().compareTo(seqStats.getScore()) > 0) {
            renderer.renderMessage(Messages.METHODICAL_LEARNING_TEST_PASSED);
            user.addPassedMethodic(sequence);
            transactionalUtil.runInNewTransaction(() -> userRepo.save(user));
        } else {
            renderer.renderMessage(Messages.METHODICAL_LEARNING_TEST_NOT_PASSED);
        }
        renderStats(seqStats);
        renderer.renderMessage(Messages.METHODICAL_LEARNING_ENDED);
        navigator.navigate(MenuStudentView.class);
    }

    private void renderStats(final SequenceStatistic seqStats) {
        StatisticRenderer.renderStepsDetails(renderer, seqStats, seqStats.getSequence().getAllowedRetries());
    }

    @Override
    public Messages getLabel() {
        return Messages.TESTING_LABEL;
    }

    @Override
    public List<String> getMessageParams() {
        return Collections.emptyList();
    }

}
