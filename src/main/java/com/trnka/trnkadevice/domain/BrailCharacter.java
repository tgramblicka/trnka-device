package com.trnka.trnkadevice.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.trnka.trnkadevice.inputreader.Keystroke;
import com.trnka.trnkadevice.ui.messages.IMessage;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "brail_character")
public class BrailCharacter implements IMessage {

    @Id
    private Long id;
    private Character character;
    private List<Keystroke> brailRepresentationKeystrokes;
    @Column(name = "brail_representation", columnDefinition = "json")
    @Type(type = "json")
    private List<Integer> brailRepresentation;
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

    public List<Keystroke> getBrailRepresentationKeystrokes() {
        return brailRepresentationKeystrokes;
    }

    public void setBrailRepresentationKeystrokes(final List<Keystroke> brailRepresentation) {
        this.brailRepresentationKeystrokes = brailRepresentation;
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
