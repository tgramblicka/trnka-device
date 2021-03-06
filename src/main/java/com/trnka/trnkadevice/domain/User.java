package com.trnka.trnkadevice.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
@EqualsAndHashCode
public class User extends BaseEntity {
    public static final Long DEFAULT_USER_ID = 1L; // static user that is always on device DB, cannot be deleted or updated.

    @Column(name = "external_id")
    private Long externalId;

    @Column(name = "username")
    private String username;

    @Column(name = "code", length = 4, unique = true)
    private String code;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<SequenceStatistic> statistics = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @Fetch(value = FetchMode.SUBSELECT)
    private Set<UserSequence> sequences = new TreeSet<>();

    /**
     * Holds info about those MethodicalLearningSequences that have been passed. Passed sequences unlock other, more advanced, sequences for user.
     * This info could be queried also from SequenceStatistics, however when want to manually unlock all sequences to some user, it would be easier to do it in
     * this table,
     * rather than filling SequenceStatistics table with fake records.
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "sequence")
    @Fetch(value = FetchMode.SUBSELECT)
    private Set<UserPassedMethodicalSequence> passedSequences = new TreeSet<>();



    public User() {
        super();
    }


    public void addAllSequnces(List<Sequence> sequences) {
        sequences.stream().forEach(this::addSequnce);
    }

    public void addSequnce(Sequence sequence) {
        getSequences().add(new UserSequence(this.getId(), sequence.getId()));
    }

    public void addAllPassedMethodics(List<MethodicalLearningSequence> sequences) {
        sequences.stream().forEach(this::addPassedMethodic);
    }

    public void addPassedMethodic(MethodicalLearningSequence methodicalSequence) {
        getPassedSequences().add(new UserPassedMethodicalSequence(this.getId(), methodicalSequence.getId()));
    }

}
