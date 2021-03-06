package com.trnka.trnkadevice.renderer;

import com.trnka.trnkadevice.ui.Renderable;
import com.trnka.trnkadevice.ui.messages.IMessage;

public interface IRenderer {

    void renderMessage(IMessage message,
                       String... params);

    void renderLabel(Renderable component,
                     String... params);

}
