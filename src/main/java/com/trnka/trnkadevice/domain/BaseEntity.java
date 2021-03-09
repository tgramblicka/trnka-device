package com.trnka.trnkadevice.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode
public abstract class BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "device_seq", sequenceName = "device_seq", allocationSize = 1, initialValue = 200)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "device_seq")
    private Long id;
}
