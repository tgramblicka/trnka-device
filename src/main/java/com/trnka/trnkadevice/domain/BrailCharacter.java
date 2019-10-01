package com.trnka.trnkadevice.domain;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.trnka.trnkadevice.inputreader.Keystroke;
import com.trnka.trnkadevice.ui.messages.IMessage;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Entity
@Table(name = "brail_character")
@TypeDef(
        name = "json",
        typeClass = JsonStringType.class
)
public class BrailCharacter implements IMessage {

    @Id
    private Long id;
    private Character character;
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

    @Transient
    public List<Keystroke> getBrailRepresentationKeystrokes() {
        return this.brailRepresentation.stream().map(Keystroke::getByCode).collect(Collectors.toList());
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

    public List<Integer> getBrailRepresentation() {
        return brailRepresentation;
    }

    public void setBrailRepresentation(final List<Integer> brailRepresentation) {
        this.brailRepresentation = brailRepresentation;
    }
}
