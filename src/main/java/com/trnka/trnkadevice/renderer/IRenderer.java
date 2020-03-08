package com.trnka.trnkadevice.renderer;

import com.trnka.trnkadevice.ui.Renderable;
import com.trnka.trnkadevice.ui.messages.Messages;

import java.util.List;

public interface IRenderer {

    void renderMessage(Renderable renderable);

    void renderMessages(List<Messages> messagesList);
}
