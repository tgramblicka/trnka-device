package com.trnka.trnkadevice.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.trnka.trnkadevice.repository.SequenceStatisticRepository;
import com.trnka.trnkadevice.repository.UserSequenceRepository;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User extends BaseEntity {
    public static final Long DEFAULT_USER_ID = 1L; // static user that is always on device DB, cannot be deleted or updated.

    @Column(name = "external_id")
    private Long externalId;

    // todo: remove column > unused due to GDPR
    @Column(name = "username")
    private String username;

    @Column(name = "code", length = 4, unique = true)
    private String code;

    @Column(name = "login_count", length = 5)
    private Integer loginCount = 0;


    public User() {
        super();
    }

    public List<UserSequence> getAllSequences(UserSequenceRepository userSequenceRepository) {
        return userSequenceRepository.findAllById_UserId(this.getId());
    }

    public List<SequenceStatistic> getStatistics(SequenceStatisticRepository seqStatsRepository) {
        return seqStatsRepository.findByUser_Id(this.getId());
    }

}
