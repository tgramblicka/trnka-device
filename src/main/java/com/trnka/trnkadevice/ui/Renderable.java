package com.trnka.trnkadevice.ui;

import com.trnka.trnkadevice.ui.messages.Messages;

import java.util.List;

public interface Renderable {

    Messages getLabel();

    List<String> getMessageParams();
}
