package com.trnka.trnkadevice.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "step")
@Data
@EqualsAndHashCode
public class Step {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "brail_character_id", referencedColumnName = "id", nullable = false)
    @NotNull
    private BrailCharacter brailCharacter;

    @Column(name = "preserve_order")
    private Boolean preserveOrder;


    public Step() {
        super();

    }
}
