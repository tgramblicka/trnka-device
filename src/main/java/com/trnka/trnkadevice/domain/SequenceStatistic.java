package com.trnka.trnkadevice.domain;

import java.math.BigDecimal;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.trnka.restapi.dto.statistics.Evaluate;
import com.trnka.trnkadevice.ui.evaluation.SequenceEvaluator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sequence_statistic")
@Getter
@Setter
@EqualsAndHashCode
// @NamedQuery(name = SequenceStatistic.FIND_ALL_TEST_STATS_FOR_USER,
// query = "SELECT stats from User user INNER JOIN user.statistics stats INNER JOIN TestingSequence as seq ON seq.id = stats.sequenceId WHERE TYPE(seq) =
// TestingSequence AND user.id = :userId")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id", name = "user_id", nullable = false)
    @NotNull
    private User user;

    @Column
    private Date createdOn;
    private Long took;

    public static SequenceStatistic create(final Sequence seq,
                                           final User user) {
        SequenceStatistic seqStats = new SequenceStatistic();
        seqStats.setSequence(seq);
        seqStats.setCreatedOn(new Date());
        user.getStatistics().add(seqStats);
        seqStats.setUser(user);
        return seqStats;
    }

    public StepStatistic addStepStatistic(final SequenceStatistic seqStats,
                                          final Step step,
                                          final long took,
                                          final Evaluate evaluationOfSequence) {
        StepStatistic stepStats = new StepStatistic();
        stepStats.setStep(step);
        stepStats.setTook(took);
        stepStats.setCorrect(evaluationOfSequence.getCorrect());
        stepStats.setRetries(evaluationOfSequence.getNegativeTries());
        seqStats.getStepStats().add(stepStats);
        return stepStats;
    }

    public BigDecimal getScore() {
        return BigDecimal.valueOf(getStepStats().stream().filter(StepStatistic::isCorrect).count()).divide(BigDecimal.valueOf(getStepStats().size()));
    }

}
