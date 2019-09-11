package com.trnka.trnkadevice.domain;

import java.util.ArrayList;
import java.util.List;

public class LearningSequence {

    private int allowedRetries;
    private long timeout;
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
}
