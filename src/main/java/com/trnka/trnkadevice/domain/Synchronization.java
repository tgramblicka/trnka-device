package com.trnka.trnkadevice.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "synchronization")
@Getter
@Setter
@EqualsAndHashCode
public class Synchronization {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "executed_on")
    private LocalDateTime executedOn;

    @NotNull
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private SyncType type;

    @NotNull
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private SyncStatus status;

}