package com.trnka.trnkadevice.ui;

import java.util.ArrayList;
import java.util.List;

import com.trnka.trnkadevice.domain.Sequence;
import com.trnka.trnkadevice.ui.messages.Messages;

public class SequenceComponent<T extends Sequence> implements Renderable {
    private T sequence;

    public SequenceComponent(final T sequence) {
        this.sequence = sequence;
    }

    @Override
    public Messages getMessage() {
        return Messages.SEQUENCE;
    }

    public T getSequence() {
        return sequence;
    }

    public void setSequence(final T sequence) {
        this.sequence = sequence;
    }

    @Override
    public List<Messages> getParams() {
        List<Messages> params = new ArrayList<>();
        params.add(this.sequence.getAudioMessage());
        getSequence().getSteps().stream().forEach(s -> params.add(s.getBrailCharacter().getLetterMessage()));
        // getSequence().getSteps().stream().map(s -> s.getBrailCharacter().getLetterMessage()).collect(Collectors.toList());
        return params;
    }
}
