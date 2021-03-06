package com.trnka.trnkadevice.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "user_sequences")
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"user, sequence" })
@ToString(exclude = {"user, sequence" })
public class UserSequence {

    @EmbeddedId
    private UserSequenceKey id;

    public UserSequence(Long userId,
                        Long sequenceId) {
        this.id = new UserSequenceKey(userId,
                                      sequenceId);
    }

    @NotNull
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", updatable = false, insertable = false)
    private User user;

    @NotNull
    @ManyToOne
    @MapsId("sequenceId")
    @JoinColumn(name = "sequence_id", updatable = false, insertable = false)
    private Sequence sequence;

}
