package com.trnka.trnkadevice.domain;

import feign.RequestLine;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue(value = "MLS")
@Getter
@Setter
@EqualsAndHashCode
public class MethodicalLearningSequence extends Sequence {

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

}
