package com.trnka.trnkadevice.ui.testing;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;

import com.trnka.restapi.dto.statistics.Evaluate;
import com.trnka.trnkadevice.domain.UserPassedMethodicalSequence;
import com.trnka.trnkadevice.repository.UserPassedMethodicsRepository;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.trnka.trnkadevice.domain.MethodicalLearningSequence;
import com.trnka.trnkadevice.domain.Step;
import com.trnka.trnkadevice.domain.User;
import com.trnka.trnkadevice.domain.SequenceStatistic;
import com.trnka.trnkadevice.exception.SequenceIdNotSetException;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.repository.MethodicalLearningSequenceRepository;
import com.trnka.trnkadevice.repository.UserRepository;
import com.trnka.trnkadevice.service.StatisticService;
import com.trnka.trnkadevice.ui.IView;
import com.trnka.trnkadevice.ui.UserSession;
import com.trnka.trnkadevice.ui.YesOrNoView;
import com.trnka.trnkadevice.ui.evaluation.SequenceEvaluator;
import com.trnka.trnkadevice.ui.interaction.UserInteractionHandler;
import com.trnka.trnkadevice.ui.learning.LettersSelectionView;
import com.trnka.trnkadevice.ui.messages.AudioMessage;
import com.trnka.trnkadevice.ui.messages.Messages;
import com.trnka.trnkadevice.ui.navigation.Navigator;
import com.trnka.trnkadevice.ui.statistic.StatisticRenderer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequiredArgsConstructor
public class LettersTestingView implements IView {

    private final IRenderer renderer;
    private final Navigator navigator;
    private final UserInteractionHandler userInteractionHandler;
    private final UserSession userSession;
    private final UserRepository userRepo;
    private final StatisticService statisticService;
    private final MethodicalLearningSequenceRepository methodicalLearningSequenceRepository;
    private final YesOrNoView yesOrNoView;
    private final UserPassedMethodicsRepository passedMethodicsRepository;

    private Long sequenceId;

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
            index++;
            renderer.renderMessage(AudioMessage.of(Messages.TESTING_TYPE_IN_CHARACTER_BRAIL, step.getBrailCharacter().getLetterMessage()));
            long start = System.currentTimeMillis();
            Integer negativeRetries = 0;
            SequenceEvaluator evaluator = new SequenceEvaluator(renderer,
                                                                userInteractionHandler);
            Evaluate evaluated = evaluator.evaluateUserInput(step, sequence.getAllowedTestRetries(), negativeRetries,
                    index + 1 == sequence.getSteps().size());
            long took = System.currentTimeMillis() - start;
            seqStats.addStepStatistic(seqStats, step, took, evaluated);
        }
        statisticService.saveMethodicalLearingTestStats(seqStats);
        boolean passedTest = seqStats.getScore().multiply(BigDecimal.valueOf(100.0D)).compareTo(sequence.getPassingRate()) > 0;
        if (passedTest) {
            renderer.renderMessage(AudioMessage.of(Messages.METHODICAL_LEARNING_TEST_PASSED));
            passedMethodicsRepository.save(new UserPassedMethodicalSequence(user,sequence));
            renderStats(seqStats);
            renderer.renderMessage(AudioMessage.of(Messages.METHODICAL_LEARNING_ENDED));
            navigator.navigateAsync(LettersSelectionView.class);
        } else {
            renderer.renderMessage(AudioMessage.of(Messages.METHODICAL_LEARNING_TEST_NOT_PASSED));
            renderStats(seqStats);

            yesOrNoView.refresh(yesSelected -> handleYesNoSelection(yesSelected, sequenceId),
                    AudioMessage.of(Messages.DO_YOU_WANT_TO_REPEAT_TESTING_SEQUENCE_YES_NO));
            navigator.navigateAsync(YesOrNoView.class);
        }
    }

    private void handleYesNoSelection(Boolean yesSelected,
                                      Long sequenceId) {
        if (yesSelected) {
            this.refresh(sequenceId);
            navigator.navigateAsync(LettersTestingView.class);
        } else {
            navigator.navigateAsync(LettersSelectionView.class);
        }
    }

    @Override
    public Class<? extends IView> onEscape() {
        return LettersSelectionView.class;
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
