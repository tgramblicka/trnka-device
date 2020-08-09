package com.trnka.trnkadevice.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.Range;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue(value = "MLS")
@Getter
@Setter
@EqualsAndHashCode
public class MethodicalLearningSequence extends Sequence implements Comparable {

    public MethodicalLearningSequence() {
        super();
    }

    @Column(name = "allowed_test_retries")
    private Integer allowedTestRetries;

    /**
     * Sequence index, if sequence with index=1 is passed, then sequence with index=2 is unlocked etc.
     */
    @Column(name = "order")
    private Integer order;

    /**
     * In % 0-100
     */
    @Range(min = 0, max = 100)
    @Column(name = "passing_rate_percentage")
    private BigDecimal passingRate;

    @Override
    public int compareTo(final Object o) {
        if (o instanceof MethodicalLearningSequence) {
            MethodicalLearningSequence other = (MethodicalLearningSequence)o;
            return this.getOrder().compareTo(other.getOrder());
        }
        return 0;
    }
}
