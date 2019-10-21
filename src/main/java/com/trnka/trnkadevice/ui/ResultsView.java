package com.trnka.trnkadevice.ui;

import com.trnka.trnkadevice.ui.navigation.Navigator;
import com.trnka.trnkadevice.ui.navigation.NavigatorUtil;
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

        NavigatorUtil.registerMainMenuNavigation(navigator);
    }

    @Override
    public Messages getLabel() {
        return Messages.RESULTS_LABEL;
    }
}
