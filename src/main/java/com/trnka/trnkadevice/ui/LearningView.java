package com.trnka.trnkadevice.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.ui.messages.Messages;

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
        NavigatorUtil.registerMainMenuNavigation(navigator);
    }

    @Override
    public Messages getViewName() {
        return Messages.LEARNING_LABEL;
    }
}
