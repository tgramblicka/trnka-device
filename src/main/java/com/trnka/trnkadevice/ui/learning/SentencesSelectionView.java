package com.trnka.trnkadevice.ui.learning;

import java.util.Collections;
import java.util.List;

import com.trnka.trnkadevice.ui.messages.AudioMessage;
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
        renderer.renderMessage(AudioMessage.of(Messages.LEARNING_SENTENCES_VIEW));
        renderer.renderMessage(AudioMessage.of(Messages.NO_SEQUENCES_FOUND));
        navigationUtils.waitForFlowBreakingButtonClik();
    }

    @Override
    public Class<? extends IView> onEscape() {
        return MethodicalLearningMenuView.class;
    }

    @Override
    public Messages getMessage() {
        return Messages.LEARNING_SENTENCES_MENU;
    }

    @Override
    public List<Messages> getParams() {
        return Collections.EMPTY_LIST;
    }
}
