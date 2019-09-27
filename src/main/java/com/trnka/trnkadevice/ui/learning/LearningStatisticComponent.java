package com.trnka.trnkadevice.ui.learning;

import com.trnka.trnkadevice.domain.statistics.SequenceStatistic;
import com.trnka.trnkadevice.ui.Renderable;
import com.trnka.trnkadevice.ui.messages.Messages;

public class LearningStatisticComponent implements Renderable {

    private SequenceStatistic sequenceStatistic;

    public LearningStatisticComponent(final SequenceStatistic sequenceStatistic) {
        this.sequenceStatistic = sequenceStatistic;
    }

    @Override
    public Messages getLabel() {
        return null;
    }

}
