package com.trnka.trnkadevice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.trnka.trnkadevice.domain.statistics.SequenceStatistic;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
@EqualsAndHashCode
public class User {

    @GeneratedValue
    @Id
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "code", length = 4)
    private String code;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id", name = "user_id")
    Set<SequenceStatistic> statistics = new HashSet<>();

    public User() {
        super();
    }

}
