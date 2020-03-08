package com.trnka.trnkadevice.ui.messages;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.trnka.trnkadevice.ui.Renderable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AudioMessage implements Renderable {
    private Messages message;
    private List<Messages> params = new ArrayList<>();

    public static AudioMessage of(Messages message) {
        AudioMessage am = new AudioMessage();
        am.setMessage(message);
        return am;
    }

    public static AudioMessage of(Messages message,
                                  List<Messages> messages) {
        AudioMessage am = new AudioMessage();
        am.setMessage(message);
        am.setParams(messages);
        return am;
    }

    public static AudioMessage of(Messages message,
                                  Messages... params) {
        return AudioMessage.of(message, Stream.of(params).collect(Collectors.toList()));
    }

    @Override
    public Messages getMessage() {
        return message;
    }

    @Override
    public List<Messages> getParams() {
        return params;
    }
}
