package com.trnka.trnkadevice.domain.statistics;

import com.trnka.trnkadevice.domain.SequenceStep;
import lombok.Data;

@Data
public class StepStatistic {
    private SequenceStep sequenceStep;
    private int retries;
    private Long took;

}
