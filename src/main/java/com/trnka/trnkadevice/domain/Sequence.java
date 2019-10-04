package com.trnka.trnkadevice.domain;

import com.trnka.trnkadevice.ui.messages.Messages;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Table(name = "sequence")
@Data
public class Sequence {

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
    @JoinColumn(referencedColumnName = "id", name = "sequence_id")
    private List<Step> steps = new ArrayList<>();

}
