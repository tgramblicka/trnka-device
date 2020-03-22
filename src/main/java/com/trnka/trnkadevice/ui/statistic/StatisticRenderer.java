package com.trnka.trnkadevice.ui.statistic;

import com.trnka.trnkadevice.domain.statistics.SequenceStatistic;
import com.trnka.trnkadevice.domain.statistics.StepStatistic;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.ui.messages.AudioMessage;
import com.trnka.trnkadevice.ui.messages.Messages;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StatisticRenderer {

    public static void renderStatisticForTest(final IRenderer renderer,
                                              final SequenceStatistic seqStats,
                                              final int maxAllowedRetries) {
        renderer.renderMessage(AudioMessage.of(Messages.STATISTIC_SEQUENCE));
        renderStepsDetails(renderer, seqStats, maxAllowedRetries);

        Long correct = seqStats.getStepStats().stream().filter(StepStatistic::isCorrect).count();
        int allStepsCount = seqStats.getStepStats().size();

        // String result = String.format("%d / %d", correct, allStepsCount);
        // calculatePercentage(correct, allStepsCount);
        List<Messages> params = Stream.of(Messages.fromNumber(correct.intValue()), Messages.Z, Messages.fromNumber(allStepsCount)).collect(Collectors.toList());

        renderer.renderMessage(AudioMessage.of(Messages.TESTING_SEQUENCE_OVERALL_RESULT, params));
    }

    // todo: think about rendering percentage
    private static String calculatePercentage(Long correct,
                                              Integer all) {
        double percentage = (correct.doubleValue() / all.doubleValue()) * 100.0D;
        return String.valueOf(percentage);
    }

    public static void renderStepsDetails(final IRenderer renderer,
                                          final SequenceStatistic seqStats,
                                          final int maxAllowedRetries) {
        seqStats.getStepStats().stream().forEach(s -> {
            String letter = s.getStep().getBrailCharacter().getText();
            int negativeRetries = s.getRetries();

            boolean correctGuess = maxAllowedRetries - negativeRetries > 0;
            Messages correctMessage = correctGuess ? Messages.CORRECT_GUESS
                                                : Messages.INCORRECT_GUESS;
            String took = String.valueOf(s.getTook() / 1000.0F); // todo : think about rendering millis


            AudioMessage audio1= AudioMessage.of(Messages.STATISTIC_LETTER, Messages.fromText(letter));
            AudioMessage audio2= AudioMessage.of(Messages.STATISTIC_LETTER_GUESSED, Messages.fromText(letter), correctMessage);
            AudioMessage audio3 = AudioMessage.of(Messages.STATISTIC_INCORRECT_RETRIES, Messages.fromNumber(negativeRetries), Messages.Z, Messages.fromNumber(maxAllowedRetries));
            renderer.renderMessage(audio1);
            renderer.renderMessage(audio2);
            renderer.renderMessage(audio3);
        });
    }
}
