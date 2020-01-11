package com.trnka.trnkadevice.ui.learning;

import com.trnka.trnkadevice.ui.IView;
import com.trnka.trnkadevice.ui.messages.Messages;

import java.util.Collections;
import java.util.List;

public class MethodicalLearningMenuView implements IView {

    @Override public void enter() {

    }

    @Override
    public Messages getLabel() {
        return Messages.METHODICAL_LEARNING_MENU;
    }

    @Override
    public List<String> getMessageParams() {
        return Collections.emptyList();
    }

}
