package com.trnka.trnkadevice.domain;

public class SequenceStep {

    private BrailCharacter brailCharacter;
    private boolean preserveOrder;

    public BrailCharacter getBrailCharacter() {
        return brailCharacter;
    }

    public void setBrailCharacter(final BrailCharacter brailCharacter) {
        this.brailCharacter = brailCharacter;
    }

    public boolean isPreserveOrder() {
        return preserveOrder;
    }

    public void setPreserveOrder(final boolean preserveOrder) {
        this.preserveOrder = preserveOrder;
    }

    public SequenceStep() {
        super();

    }
}
