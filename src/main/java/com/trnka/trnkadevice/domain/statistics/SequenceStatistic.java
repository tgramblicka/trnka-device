package com.trnka.trnkadevice.domain.statistics;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "sequence_statistic")
public class SequenceStatistic {

    @GeneratedValue
    @Id
    private Long id;

    private Long sequence;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id" , name = "sequence_statistic_id")
    private List<StepStatistic> stepStats = new ArrayList<>();

    @Column private Date createdOn;
    private Long took;

}
