package com.trnka.trnkadevice.domain;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_sequences")
@Getter
@Setter
@NoArgsConstructor
public class UserSequence implements Serializable {

    @EmbeddedId
    private UserSequenceKey id;

    public UserSequence(Long userId,
                        Long sequenceId) {
        this.id = new UserSequenceKey(userId,
                                      sequenceId);
    }

    public UserSequence(User user,
                        Sequence sequence) {
        this.id = new UserSequenceKey(user.getId(), sequence.getId());
        this.user = user;
        this.sequence = sequence;
    }


//    @NotNull
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", updatable = false, insertable = false)
    private User user;

//    @NotNull
    @ManyToOne
    @MapsId("sequenceId")
    @JoinColumn(name = "sequence_id", updatable = false, insertable = false)
    private Sequence sequence;

//    public UserSequenceKey getId() {
//        return id;
//    }
//
//    public void setId(final UserSequenceKey id) {
//        this.id = id;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(final User user) {
//        this.user = user;
//    }
//
//    public Sequence getSequence() {
//        return sequence;
//    }
//
//    public void setSequence(final Sequence sequence) {
//        this.sequence = sequence;
//    }
}
