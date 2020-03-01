package com.trnka.trnkadevice.renderer;

import com.trnka.trnkadevice.sound.PlaySound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.trnka.trnkadevice.ui.Renderable;
import com.trnka.trnkadevice.ui.messages.IMessage;

@Service
@Profile("dev")
@Slf4j
public class AudioRenderer implements IRenderer {


    @Autowired
    private PlaySound playSound;

    // https://stackoverflow.com/questions/27283729/how-to-play-sound-in-java
    @Override
    public void renderMessage(final IMessage message,
                              final String... params) {
        log.info(">>> " + message.getText() + " <<<", params);
        playSound.playSound(message.getAudioFile());
    }

    @Override
    public void renderLabel(final Renderable component,
                            final String... params) {
        renderMessage(component.getLabel(), params);
    }
}
