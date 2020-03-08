package com.trnka.trnkadevice.ui;

import java.util.List;
import java.util.stream.Collectors;

import com.trnka.trnkadevice.domain.Sequence;
import com.trnka.trnkadevice.ui.messages.Messages;

public class SequenceComponent<T extends Sequence> implements Renderable {
    private T sequence;

    public SequenceComponent(final T sequence) {
        this.sequence = sequence;
    }

    @Override
    public Messages getMessage() {
        return this.sequence.getAudioMessage();
    }

    public T getSequence() {
        return sequence;
    }

    public void setSequence(final T sequence) {
        this.sequence = sequence;
    }

    @Override
    public List<Messages> getParams() {
        return getSequence().getSteps().stream().map(s -> s.getBrailCharacter().getLetterMessage()).collect(Collectors.toList());
    }
}
