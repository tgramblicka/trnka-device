package com.trnka.trnkadevice.ui.results;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.domain.statistics.SequenceStatistic;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.ui.IView;
import com.trnka.trnkadevice.ui.messages.Messages;
import com.trnka.trnkadevice.ui.statistic.StatisticRenderer;

@Component
public class ResultView implements IView {

    private SequenceStatistic sequenceStatistic;

    private IRenderer renderer;

    @Autowired
    public ResultView(final IRenderer renderer) {
        this.renderer = renderer;
    }

    public void refresh(SequenceStatistic sequenceStatistic) {
        this.sequenceStatistic = sequenceStatistic;
    }

    @Override
    public void enter() {
        renderStats(sequenceStatistic);
    }

    private void renderStats(final SequenceStatistic seqStats) {
        StatisticRenderer.renderStatisticForTest(renderer, seqStats, seqStats.getSequence().getAllowedRetries());
    }

    @Override
    public Messages getLabel() {
        return Messages.RESULTS_VIEW;
    }

    @Override
    public List<String> getMessageParams() {
        return Collections.emptyList();
    }
}
