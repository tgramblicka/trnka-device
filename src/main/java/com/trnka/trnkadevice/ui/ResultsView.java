package com.trnka.trnkadevice.ui;

import com.trnka.trnkadevice.inputreader.InputReader;
import com.trnka.trnkadevice.inputreader.Keystroke;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.ui.messages.Messages;

@Component
public class ResultsView implements IView {

    private IRenderer renderer;
    private Navigator navigator;

    @Autowired
    public ResultsView(final IRenderer renderer,
                       Navigator navigator) {
        this.renderer = renderer;
        this.navigator = navigator;
    }

    @Override
    public void enter() {
        renderer.renderMessage(Messages.RESULTS_VIEW);

        NavigatorUtil.mainMenuNavigation(navigator);
    }

    @Override
    public Messages getViewName() {
        return Messages.RESULTS_LABEL;
    }
}
