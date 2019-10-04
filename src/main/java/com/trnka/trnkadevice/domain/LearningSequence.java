package com.trnka.trnkadevice.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "learning_sequence")
@Data
@DiscriminatorValue(value = "LS")
public class LearningSequence extends Sequence {

    public LearningSequence() {
        super();
    }

}
