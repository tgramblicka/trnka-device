package com.trnka.trnkadevice.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "TS")
public class TestingSequence extends Sequence {

    public TestingSequence() {
        super();
    }
}
