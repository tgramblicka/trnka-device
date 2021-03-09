package com.trnka.trnkadevice.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
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


    public User() {
        super();
    }

}
