package com.trnka.trnkadevice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserSequenceKey implements Serializable {
    @Column(name = "user_id")
    private Long userId;


    @Column(name = "sequence_id")
    private Long sequenceId;

}
