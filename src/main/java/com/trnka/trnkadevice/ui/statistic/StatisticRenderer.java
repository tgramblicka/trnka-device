package com.trnka.trnkadevice.ui.statistic;

import com.trnka.trnkadevice.domain.statistics.SequenceStatistic;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.ui.messages.Messages;

public class StatisticRenderer {

    public void renderStatistic(final IRenderer renderer,
                                final SequenceStatistic seqStats) {

        StringBuilder b = new StringBuilder();
        seqStats.getStepStats().stream().forEach(s -> {
            String letter = s.getStep().getBrailCharacter().getText();
            int negativeRetries = s.getRetries();

            int maxAllowedRetries = seqStats.getSequence().getAllowedRetries();
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
