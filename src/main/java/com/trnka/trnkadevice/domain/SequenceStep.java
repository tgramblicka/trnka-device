package com.trnka.trnkadevice.domain;

import com.trnka.trnkadevice.inputreader.Keystroke;

import java.util.List;

public class SequenceStep {

    private Character character;
    private List<Keystroke> brailRepresentation;
    private boolean preserveOrder;

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(final Character character) {
        this.character = character;
    }

    public List<Keystroke> getBrailRepresentation() {
        return brailRepresentation;
    }

    public void setBrailRepresentation(final List<Keystroke> brailRepresentation) {
        this.brailRepresentation = brailRepresentation;
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
