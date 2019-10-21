package com.trnka.trnkadevice.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "LS")
public class LearningSequence extends Sequence {


    public LearningSequence() {
        super();
    }

}
