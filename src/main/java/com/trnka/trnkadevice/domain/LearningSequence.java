package com.trnka.trnkadevice.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue(value = "LS")
public class LearningSequence extends Sequence {

    @NotNull
    @Column(name = "external_id")
    private Long externalId;

    public LearningSequence() {
        super();
    }

}
