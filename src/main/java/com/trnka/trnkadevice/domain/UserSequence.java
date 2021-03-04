package com.trnka.trnkadevice.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "user_sequences")
@NoArgsConstructor
@Data
public class UserSequence {

    public UserSequence(final Long userId, final Long sequenceId) {
         this.id = new UserSequenceKey(userId, sequenceId);
    }

    @EmbeddedId
    private UserSequenceKey id;


    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("sequenceId")
    @JoinColumn(name = "sequence_id")
    private Sequence sequence;

}
