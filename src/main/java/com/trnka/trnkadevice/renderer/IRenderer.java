package com.trnka.trnkadevice.renderer;

import com.trnka.trnkadevice.ui.messages.Messages;

public interface IRenderer {

    void renderMessage(Messages message, String... params);

}
