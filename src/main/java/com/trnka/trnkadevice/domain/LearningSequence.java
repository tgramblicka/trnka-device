package com.trnka.trnkadevice.domain;

import com.trnka.trnkadevice.ui.messages.Messages;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "learning_sequence")
public class LearningSequence {

    @Id
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "audio_message")
    private Messages audioMessage;

    @Column(name = "allowed_retries")
    private int allowedRetries;
    @Column(name = "timeout")
    private long timeout;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id", name = "learning_sequence_id")
    private List<SequenceStep> steps = new ArrayList<>();

    public LearningSequence() {
        super();
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

    public List<SequenceStep> getSteps() {
        return steps;
    }

    public void setSteps(final List<SequenceStep> steps) {
        this.steps = steps;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public Messages getAudioMessage() {
        return audioMessage;
    }

    public void setAudioMessage(final Messages audioMessage) {
        this.audioMessage = audioMessage;
    }
}
