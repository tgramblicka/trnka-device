package com.trnka.trnkadevice.renderer;

import javax.inject.Singleton;

import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.ui.messages.Messages;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
@Component
public class ConsolRenderer implements IRenderer {

    @Override
    public void renderMessage(final Messages message, String... params) {
        log.info(">>> " + message.getText() + " <<<", params);
    }
}
