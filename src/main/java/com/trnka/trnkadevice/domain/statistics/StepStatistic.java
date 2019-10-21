package com.trnka.trnkadevice.domain.statistics;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.trnka.trnkadevice.domain.Step;

import lombok.Data;

@Data
@Entity
@Table(name = "step_statistic")
public class StepStatistic {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "step_id", referencedColumnName = "id", nullable = false)
    @NotNull
    private Step Step;

    private int retries;
    private Long took;

}
