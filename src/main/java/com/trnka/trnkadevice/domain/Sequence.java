package com.trnka.trnkadevice.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.trnka.trnkadevice.ui.messages.Messages;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Table(name = "sequence")
public abstract class Sequence {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "audio_message")
    private Messages audioMessage;

    @Column(name = "allowed_retries")
    private int allowedRetries;
    @Column(name = "timeout")
    private long timeout;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id", name = "sequence_id")
    private List<Step> steps = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Messages getAudioMessage() {
        return audioMessage;
    }

    public void setAudioMessage(final Messages audioMessage) {
        this.audioMessage = audioMessage;
    }

    public int getAllowedRetries() {
        return allowedRetries;
    }

    public void setAllowedRetries(final int allowedRetries) {
        this.allowedRetries = allowedRetries;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(final long timeout) {
        this.timeout = timeout;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(final List<Step> steps) {
        this.steps = steps;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Sequence sequence = (Sequence) o;
        return id.equals(sequence.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
