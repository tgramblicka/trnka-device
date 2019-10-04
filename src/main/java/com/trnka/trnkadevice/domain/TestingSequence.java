package com.trnka.trnkadevice.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Data;

@Entity
@Data
@DiscriminatorValue(value = "T")
public class TestingSequence extends LearningSequence {

}
