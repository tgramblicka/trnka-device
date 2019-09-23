package com.trnka.trnkadevice.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class User {

    @GeneratedValue
    @Id
    private Long id;

    private String username;

    public User() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }
}
