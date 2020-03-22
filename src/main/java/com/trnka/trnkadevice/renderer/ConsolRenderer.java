package com.trnka.trnkadevice.renderer;

import com.trnka.trnkadevice.ui.Renderable;

import com.trnka.trnkadevice.ui.messages.Messages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
public class ConsolRenderer implements IRenderer {

    @Override
    public void renderMessage(final Renderable message) {
        if (CollectionUtils.isEmpty(message.getParams())) {
            log.info(">>> {} <<<", message.getMessage().getText());
            return;
        }
        if (message.getMessage().getParameterCount() == message.getParams().size()){
            log.info(">>> " + message.getMessage().getText()  +"<<<", message.getParams().toArray(new Messages[0]));
            return;
        }
        log.info(">>> {} <<<", message.getMessage().getText());
        renderMessages(message.getParams());



    }

    @Override
    public void renderMessages(final List<Messages> messagesList) {
        log.info(">>> {} <<<", messagesList);
    }
}
