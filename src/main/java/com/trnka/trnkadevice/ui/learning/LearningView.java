package com.trnka.trnkadevice.ui.learning;

import com.trnka.trnkadevice.dao.LearningSequenceDAO;
import com.trnka.trnkadevice.domain.LearningSequence;
import com.trnka.trnkadevice.ui.IView;
import com.trnka.trnkadevice.ui.Navigator;
import com.trnka.trnkadevice.ui.NavigatorUtil;
import com.trnka.trnkadevice.ui.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.ui.messages.Messages;

import java.util.Set;

@Component
public class LearningView implements IView {

    private Navigator navigator;
    private IRenderer renderer;
    private LearningSequenceDAO learningSequenceDAO;
    private UserSession userSession;

    @Autowired
    public LearningView(final Navigator navigator,
                        final IRenderer renderer,
                        final LearningSequenceDAO learningSequenceDAO,
                        final UserSession userSession) {
        this.navigator = navigator;
        this.renderer = renderer;
        this.learningSequenceDAO = learningSequenceDAO;
        this.userSession = userSession;
    }

    @Override
    public void enter() {
        renderer.renderMessage(Messages.LEARNING_VIEW);
        NavigatorUtil.registerMainMenuNavigation(navigator);
        Set<LearningSequence> sequences = learningSequenceDAO.getLearningSequences(userSession.getUser().getUserName());

    }

    @Override
    public Messages getViewName() {
        return Messages.LEARNING_LABEL;
    }
}
