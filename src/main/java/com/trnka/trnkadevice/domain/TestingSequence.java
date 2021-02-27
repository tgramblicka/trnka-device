package com.trnka.trnkadevice.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue(value = "TS")
public class TestingSequence extends Sequence {

    @NotNull
    @Column(name = "external_id")
    private Long externalId;

    public TestingSequence() {
        super();
    }
}
