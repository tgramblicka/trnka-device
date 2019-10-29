package com.trnka.trnkadevice.domain.statistics;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.trnka.trnkadevice.domain.Sequence;
import com.trnka.trnkadevice.domain.Step;
import com.trnka.trnkadevice.ui.evaluation.SequenceEvaluator;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "sequence_statistic")
@EqualsAndHashCode
// todo add treat statement for ONLY test stats
@NamedQuery(name = SequenceStatistic.FIND_ALL_TEST_STATS_FOR_USER,
            query = "SELECT stats from User user JOIN user.statistics stats JOIN TREAT (stats.sequence AS LearningSequence) seq WHERE user.id = :userId")
public class SequenceStatistic {
    public static final String FIND_ALL_TEST_STATS_FOR_USER = "SequenceStatistic.findAllTestResultsForUser";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id", name = "sequence_id", nullable = true)
    private Sequence sequence;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id", name = "sequence_statistic_id", nullable = true)
    private List<StepStatistic> stepStats = new ArrayList<>();

    @Column
    private Date createdOn;
    private Long took;

    public static SequenceStatistic create(final Sequence seq) {
        SequenceStatistic seqStats = new SequenceStatistic();
        seqStats.setSequence(seq);
        seqStats.setCreatedOn(new Date());
        return seqStats;
    }

    public StepStatistic addStepStatistic(final SequenceStatistic seqStats,
                                          final Step step,
                                          final long took,
                                          final SequenceEvaluator.Evaluate evaluationOfSequence) {
        StepStatistic stepStats = new StepStatistic();
        stepStats.setStep(step);
        stepStats.setTook(took);
        stepStats.setCorrect(evaluationOfSequence.getCorrect());
        stepStats.setRetries(evaluationOfSequence.getNegativeTries());
        seqStats.getStepStats().add(stepStats);
        return stepStats;
    }

}
