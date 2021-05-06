package com.trnka.trnkadevice.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.trnka.restapi.dto.statistics.Evaluate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "sequence_statistic")
@Getter
@Setter
@EqualsAndHashCode
public class SequenceStatistic extends BaseEntity {
    public static final String FIND_ALL_TEST_STATS_FOR_USER = "SequenceStatistic.findAllTestResultsForUser";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id", name = "sequence_id")
    private Sequence sequence;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(referencedColumnName = "id", name = "sequence_statistic_id")
    private List<StepStatistic> stepStats = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id", name = "user_id", nullable = false)
    @NotNull
    private User user;

    @CreationTimestamp
    @Column(name = "created_on")
    private LocalDateTime createdOn;

    private Long took;

    private Boolean passed = false;

    public static SequenceStatistic create(final Sequence seq,
                                           final User user) {
        SequenceStatistic seqStats = new SequenceStatistic();
        seqStats.setSequence(seq);
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
