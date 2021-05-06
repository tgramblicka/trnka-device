package com.trnka.trnkadevice.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.trnka.trnkadevice.domain.visitor.SequenceVisitor;
import com.trnka.trnkadevice.ui.messages.Messages;

import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Table(name = "sequence")
@Getter
@Setter
public abstract class Sequence extends BaseEntity {

    @Column(name = "external_id")
    private Long externalId;

    @Enumerated(EnumType.STRING)
    @Column(name = "audio_message")
    private Messages audioMessage;

    @Column(name = "allowed_retries")
    private int allowedRetries;
    @Column(name = "timeout")
    private long timeout;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id", name = "sequence_id")
    private List<Step> steps = new ArrayList<>();


    public List<Messages> getAllStepsAsMessagesList(){
        return getSteps().stream().map(Step::getBrailCharacter).map(BrailCharacter::getLetterMessage).collect(Collectors.toList());
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Sequence sequence = (Sequence) o;
        return getId().equals(sequence.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public abstract <T> T accept(final SequenceVisitor<T> visitor) ;

}
