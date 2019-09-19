package com.trnka.trnkadevice.renderer;

import javax.inject.Singleton;

import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.ui.Renderable;
import com.trnka.trnkadevice.ui.messages.IMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
@Component
public class ConsolRenderer implements IRenderer {

    @Override
    public void renderMessage(final IMessage message,
                              String... params) {
        log.info(">>> " + message.getText() + " <<<", params);
    }

    @Override
    public void renderLabel(final Renderable component) {
        log.info(">>> " + component.getLabel().getText() + " <<<");
    }
}
