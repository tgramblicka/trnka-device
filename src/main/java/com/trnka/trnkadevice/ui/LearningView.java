package com.trnka.trnkadevice.ui;

import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.ui.messages.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class LearningView implements IView {

    private Navigator navigator;
    private IRenderer renderer;

    @Autowired
    public LearningView(final Navigator navigator,
                        final IRenderer renderer) {
        this.navigator = navigator;
        this.renderer = renderer;
    }

    @Override
    public void enter() {
        renderer.renderMessage(Messages.LEARNING_VIEW);
        NavigatorUtil.mainMenuNavigation(navigator);
    }

    @Override
    public Messages getViewName() {
        return Messages.LEARNING_LABEL;
    }
}
