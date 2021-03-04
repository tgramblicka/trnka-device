package com.trnka.trnkadevice.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "passed_methodics")
@NoArgsConstructor
public class UserPassedMethodicalSequence {

    @EmbeddedId
    private UserSequenceKey id;


    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("sequenceId")
    @JoinColumn(name = "sequence_id")
    private MethodicalLearningSequence sequence;

    public UserPassedMethodicalSequence(final Long userId, final Long sequenceId) {
        this.id = new UserSequenceKey(userId, sequenceId);
    }


}
