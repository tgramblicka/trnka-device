package com.trnka.trnkadevice.domain;

import com.trnka.trnkadevice.inputreader.Keystroke;
import com.trnka.trnkadevice.ui.messages.IMessage;

import java.util.List;

public class BrailCharacter implements IMessage {
    private Long id;
    private Character character;
    private List<Keystroke> brailRepresentation;
    private String audioFile;

    public BrailCharacter() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

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

    public void setAudioFile(final String audioFile) {
        this.audioFile = audioFile;
    }

    @Override
    public String getText() {
        return String.valueOf(character);
    }

    @Override
    public String getAudioFile() {
        return null;
    }
}
