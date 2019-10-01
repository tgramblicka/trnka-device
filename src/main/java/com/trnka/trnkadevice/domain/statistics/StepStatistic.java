package com.trnka.trnkadevice.domain.statistics;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.trnka.trnkadevice.domain.SequenceStep;

import lombok.Data;

@Data
@Entity
@Table(name = "step_statistic")
public class StepStatistic {
    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "sequence_step_id", referencedColumnName = "id")
    private SequenceStep sequenceStep;
    private int retries;
    private Long took;

}
