package com.trnka.trnkadevice.ui;

import com.trnka.trnkadevice.domain.Sequence;
import com.trnka.trnkadevice.ui.messages.Messages;

import java.util.Collections;
import java.util.List;

public class SequenceComponent<T extends Sequence> implements Renderable {
    private T sequence;

    public SequenceComponent(final T sequence) {
        this.sequence = sequence;
    }

    @Override
    public Messages getLabel() {
        return this.sequence.getAudioMessage();
    }

    public T getSequence() {
        return sequence;
    }

    public void setSequence(final T sequence) {
        this.sequence = sequence;
    }

    @Override
    public List<String> getMessageParams() {
        return Collections.emptyList();
    }
}
