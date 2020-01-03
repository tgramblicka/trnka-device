package com.trnka.trnkadevice.ui.statistic;

import com.trnka.trnkadevice.domain.statistics.SequenceStatistic;
import com.trnka.trnkadevice.domain.statistics.StepStatistic;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.ui.messages.Messages;

public class StatisticRenderer {

    public static void renderStatisticForTest(final IRenderer renderer,
                                              final SequenceStatistic seqStats,
                                              final int maxAllowedRetries) {
        renderStepsDetails(renderer, seqStats, maxAllowedRetries);

        long correct = seqStats.getStepStats().stream().filter(StepStatistic::isCorrect).count();
        String result = String.format("%d / %d", correct, seqStats.getStepStats().size());
        renderer.renderMessage(Messages.TESTING_SEQUENCE_OVERALL_RESULT, result);
    }

    public static void renderStepsDetails(final IRenderer renderer,
                                          final SequenceStatistic seqStats,
                                          final int maxAllowedRetries) {
        StringBuilder b = new StringBuilder();
        seqStats.getStepStats().stream().forEach(s -> {
            String letter = s.getStep().getBrailCharacter().getText();
            int negativeRetries = s.getRetries();

            boolean correctGuess = maxAllowedRetries - negativeRetries > 0;
            String correctString = correctGuess ? "Spravne"
                                                : "Nespravne";
            String took = String.valueOf(s.getTook() / 1000.0F);
            String line = String.format("Pismeno %s udadnute %s. Pocet nespravnych pokusov: %s/%s. Trvanie: %s sekund", letter, correctString, negativeRetries,
                    maxAllowedRetries, took);
            b.append(line);
            b.append("\n");
        });
        renderer.renderMessage(Messages.SEQUENCE_STATISTIC, b.toString());
    }
}
