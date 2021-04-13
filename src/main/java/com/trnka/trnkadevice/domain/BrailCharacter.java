package com.trnka.trnkadevice.domain;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.trnka.trnkadevice.inputreader.Keystroke;
import com.trnka.trnkadevice.ui.messages.IMessage;
import com.trnka.trnkadevice.ui.messages.Messages;
import com.vladmihalcea.hibernate.type.json.JsonStringType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "brail_character")
@TypeDef(name = "json", typeClass = JsonStringType.class)
@Getter
@Setter
@EqualsAndHashCode
public class BrailCharacter extends BaseEntity implements IMessage {

    @Column(name = "letter", unique = true)
    private String letter;
    @Column(name = "brail_representation", columnDefinition = "json")
    @Type(type = "json")
    private List<Integer> brailRepresentation;
    @Column(name = "audio_file")
    private String audioFile;

    public BrailCharacter() {
        super();
    }

    @Transient
    public List<Keystroke> getBrailRepresentationKeystrokes() {
        return this.brailRepresentation.stream().map(String::valueOf).map(Keystroke::getByValue).collect(Collectors.toList());
    }

    @Transient
    public String getBrailRepresentationAsString() {
        return this.brailRepresentation.stream().map(String::valueOf).collect(Collectors.joining(", "));
    }

    @Transient
    public List<Messages> getBrailRepresentationAsMessages() {
        return this.brailRepresentation.stream().map(number -> Messages.fromNumber(number)).collect(Collectors.toList());
    }

    @Transient
    public String getBrailRepresentationAsText() {
        return this.brailRepresentation.stream().map(String::valueOf).collect(Collectors.joining(", "));
    }


    @Transient
    public Messages getLetterMessage() {
        return Messages.fromText(this.letter);
    }



    @Override
    public String getText() {
        return letter;
    }

    @Override
    public String getAudioFile() {
        return null;
    }

}
