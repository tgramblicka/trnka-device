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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
public class User {

    @GeneratedValue
    @Id
    private Long id;

    @Column(name = "external_id")
    private Long externalId;

    @Column(name = "username")
    private String username;

    @Column(name = "code", length = 4, unique = true)
    private String code;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<SequenceStatistic> statistics = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    @OrderBy("order ASC")
    @JoinTable(name = "user_sequences",
            joinColumns = @JoinColumn(referencedColumnName = "id", name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "sequence_id"))
    private Set<Sequence> sequences = new TreeSet<>();

    /**
     * Holds info about those MethodicalLearningSequences that have been passed. Passed sequences unlock other, more advanced, sequences for user.
     * This info could be queried also from SequenceStatistics, however when want to manually unlock all sequences to some user, it would be easier to do it in
     * this table,
     * rather than filling SequenceStatistics table with fake records.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @OrderBy("order ASC")
    @JoinTable(name = "passed_methodics",
               joinColumns = @JoinColumn(referencedColumnName = "id", name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "sequence_id"))
    private SortedSet<MethodicalLearningSequence> passedSequences = new TreeSet<>();



    public User() {
        super();
    }

    public void addSequnce(Sequence sequence) {
        getSequences().add(sequence);
    }


    public void addPassedMethodic(MethodicalLearningSequence methodicalSequence) {
        getPassedSequences().add(methodicalSequence);
    }

}
