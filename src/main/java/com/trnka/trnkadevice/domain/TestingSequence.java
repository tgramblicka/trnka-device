package com.trnka.trnkadevice.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.Range;

import com.trnka.trnkadevice.domain.visitor.SequenceVisitor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue(value = "TS")
@Getter
@Setter
@EqualsAndHashCode
public class TestingSequence extends Sequence {

    public TestingSequence() {
        super();
    }

    /**
     * In % 0-100
     */
    @Range(min = 0, max = 100)
    @Column(name = "passing_rate_percentage")
    private BigDecimal passingRate;

    @Override
    public <T> T accept(final SequenceVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
