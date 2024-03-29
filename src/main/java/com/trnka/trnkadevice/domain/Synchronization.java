package com.trnka.trnkadevice.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "synchronization")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Synchronization extends BaseEntity {

    @CreationTimestamp
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
