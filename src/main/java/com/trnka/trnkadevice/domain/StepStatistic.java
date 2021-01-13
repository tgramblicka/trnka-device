package com.trnka.trnkadevice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "step_statistic")
@EqualsAndHashCode
public class StepStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "step_id", referencedColumnName = "id", nullable = false)
    @NotNull
    private Step step;

    @Column(name = "retries")
    private Integer retries;

    @Column(name = "correct")
    private boolean correct;

    private Long took;

}
