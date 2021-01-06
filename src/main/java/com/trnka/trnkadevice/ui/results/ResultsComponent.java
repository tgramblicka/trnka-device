package com.trnka.trnkadevice.ui.results;

import com.trnka.trnkadevice.domain.SequenceStatistic;
import com.trnka.trnkadevice.ui.Renderable;
import com.trnka.trnkadevice.ui.messages.Messages;

import java.util.List;

public class ResultsComponent implements Renderable {

    // private final Messages label;
    private final SequenceStatistic statistic;

    public ResultsComponent(final SequenceStatistic statistic) {
        this.statistic = statistic;
    }

    public SequenceStatistic getStatistic() {
        return statistic;
    }

    @Override
    public Messages getMessage() {
        return Messages.TEST_RESULT_COMPONENT_LABEL;
    }

    @Override
public List<Messages> getParams() {
        return null;
    }

}
