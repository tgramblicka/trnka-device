package com.trnka.trnkadevice.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "sequence_step")
@Data
public class SequenceStep {

    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "brail_character_id")
    @NotNull
    private BrailCharacter brailCharacter;

    @Column(name = "preserve_order")
    private Boolean preserveOrder;


    public SequenceStep() {
        super();

    }
}
