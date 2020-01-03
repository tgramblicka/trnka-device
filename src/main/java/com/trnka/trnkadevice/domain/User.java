package com.trnka.trnkadevice.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.trnka.trnkadevice.domain.statistics.SequenceStatistic;

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

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "code", length = 4)
    private String code;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    // @JoinColumn(referencedColumnName = "id", name = "user_id")
    List<SequenceStatistic> statistics = new ArrayList<>();

    public User() {
        super();
    }
}
