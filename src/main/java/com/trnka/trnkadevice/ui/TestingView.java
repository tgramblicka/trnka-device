package com.trnka.trnkadevice.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.ui.messages.Messages;

@Component
public class TestingView implements IView {

    private Navigator navigator;
    private IRenderer renderer;

    @Autowired
    public TestingView(final Navigator navigator,
                       final IRenderer renderer) {
        this.navigator = navigator;
        this.renderer = renderer;
    }

    @Override
    public void enter() {
        renderer.renderMessage(Messages.TESTING_VIEW);
        NavigatorUtil.registerMainMenuNavigation(navigator);
    }

    @Override
    public Messages getViewName() {
        return Messages.TESTING_LABEL;
    }
}
