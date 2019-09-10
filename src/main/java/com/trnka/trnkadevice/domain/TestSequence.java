package com.trnka.trnkadevice.domain;

import java.util.List;

public class TestSequence {

    private int allowedRetries;
    private long timeout;
    private List<SequenceStep> steps;

    public TestSequence() {
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
