package com.trnka.trnkadevice.domain.statistics;

import com.trnka.trnkadevice.domain.LearningSequence;
import com.trnka.trnkadevice.domain.User;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class SequenceStatistic {

    @GeneratedValue
    @Id
    private Long id;
    private LearningSequence sequence;
    private List<StepStatistic> stepStats = new ArrayList<>();
    private User user;
    private Date createdOn;
    private Long took;

}
