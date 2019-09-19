package com.trnka.trnkadevice.ui.learning;

import com.trnka.trnkadevice.domain.LearningSequence;
import com.trnka.trnkadevice.ui.Renderable;
import com.trnka.trnkadevice.ui.messages.Messages;

public class LearningSequenceComponent implements Renderable {
    private LearningSequence sequence;

    public LearningSequenceComponent(final LearningSequence sequence) {
        this.sequence = sequence;
    }

    @Override
    public Messages getLabel() {
        return this.sequence.getAudioMessage();
    }

    public LearningSequence getSequence() {
        return sequence;
    }

    public void setSequence(final LearningSequence sequence) {
        this.sequence = sequence;
    }
}
