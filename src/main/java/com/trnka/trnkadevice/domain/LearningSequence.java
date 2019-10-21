package com.trnka.trnkadevice.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Data;

@Entity
@Data
@DiscriminatorValue(value = "LS")
public class LearningSequence extends Sequence {


    public LearningSequence() {
        super();
    }

}
