package com.trnka.trnkadevice.ui.learning;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.ui.IView;
import com.trnka.trnkadevice.ui.messages.Messages;
import com.trnka.trnkadevice.ui.navigation.NavigationUtils;

@Component

public class SentencesSelectionView implements IView {
    private IRenderer renderer;
    private NavigationUtils navigationUtils;

    @Autowired
    public SentencesSelectionView(final IRenderer renderer, final NavigationUtils navigationUtils) {
        this.renderer = renderer;
        this.navigationUtils = navigationUtils;
    }

    @Override
    public void enter() {
        renderer.renderMessage(Messages.LEARNING_SENTENCES_VIEW);
        renderer.renderMessage(Messages.NO_SEQUENCES_FOUND);
        NavigationUtils navUtils = new NavigationUtils();
        navUtils.waitForMenuClick();
    }

    @Override
    public Messages getLabel() {
        return Messages.LEARNING_SENTENCES_MENU;
    }

    @Override
    public List<String> getMessageParams() {
        return Collections.EMPTY_LIST;
    }
}
