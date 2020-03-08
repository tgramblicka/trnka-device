package com.trnka.trnkadevice.renderer;

import com.trnka.trnkadevice.ui.Renderable;

import com.trnka.trnkadevice.ui.messages.Messages;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ConsolRenderer implements IRenderer {

    @Override
    public void renderMessage(final Renderable message) {
        log.info(">>> " + message.getMessage().getText() + " <<<", message.getParams());
    }

    @Override public void renderMessages(final List<Messages> messagesList) {
        log.info(">>> {} <<<", messagesList);
    }
}
