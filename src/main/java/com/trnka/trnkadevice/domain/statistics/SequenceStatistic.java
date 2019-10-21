package com.trnka.trnkadevice.domain.statistics;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "sequence_statistic")
@EqualsAndHashCode
public class SequenceStatistic {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "sequence_id", updatable = false, insertable = false)
    private Long sequenceId;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id" , name = "sequence_statistic_id", nullable = true)
    private List<StepStatistic> stepStats = new ArrayList<>();

    @Column private Date createdOn;
    private Long took;

}
