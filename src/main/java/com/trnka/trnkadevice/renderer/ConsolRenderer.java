package com.trnka.trnkadevice.renderer;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.trnka.trnkadevice.ui.Renderable;
import com.trnka.trnkadevice.ui.messages.IMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Profile("dev")
public class ConsolRenderer implements IRenderer {

    @Override
    public void renderMessage(final IMessage message,
                              String... params) {
        log.info(">>> " + message.getText() + " <<<", params);
    }

    @Override
    public void renderLabel(final Renderable component,
                            final String... params) {
        renderMessage(component.getLabel(), params);
    }
}
