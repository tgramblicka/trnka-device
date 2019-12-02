package com.trnka.trnkadevice.ui.results;

import java.util.List;

import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.domain.statistics.SequenceStatistic;
import com.trnka.trnkadevice.ui.IView;
import com.trnka.trnkadevice.ui.messages.Messages;

@Component
public class ResultView implements IView {

    private SequenceStatistic sequenceStatistic;

    public void refresh(SequenceStatistic sequenceStatistic) {
        this.sequenceStatistic = sequenceStatistic;
    }

    @Override
    public void enter() {

    }

    @Override
    public Messages getLabel() {
        return null;
    }

    @Override
    public List<String> getMessageParams() {
        return null;
    }
}
