package com.trnka.trnkadevice.domain;

import com.trnka.trnkadevice.domain.visitor.SequenceVisitor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "LS")
public class LearningSequence extends Sequence {

    public LearningSequence() {
        super();
    }

    @Override
    public <T> T accept(final SequenceVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
