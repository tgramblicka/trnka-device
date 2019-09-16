package com.trnka.trnkadevice.ui;

import com.trnka.trnkadevice.ui.messages.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LearningCharacterSelectionView implements IView {

    private Navigator navigator;

    @Autowired
    public LearningCharacterSelectionView(final Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void enter() {

    }

    @Override
    public Messages getLabel() {
        return Messages.LEARNING_CHARACTER_VIEW_LABEL;
    }
}
