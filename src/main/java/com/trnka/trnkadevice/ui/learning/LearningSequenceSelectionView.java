package com.trnka.trnkadevice.ui.learning;

import com.trnka.trnkadevice.dao.LearningSequenceDAO;
import com.trnka.trnkadevice.domain.LearningSequence;
import com.trnka.trnkadevice.ui.CycledMenuComponent;
import com.trnka.trnkadevice.ui.IView;
import com.trnka.trnkadevice.ui.navigation.Navigator;
import com.trnka.trnkadevice.ui.navigation.NavigatorUtil;
import com.trnka.trnkadevice.ui.Renderable;
import com.trnka.trnkadevice.ui.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.ui.messages.Messages;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class LearningSequenceSelectionView implements IView {

    private List<? extends Renderable> menu;

    private Navigator navigator;
    private IRenderer renderer;
    private LearningSequenceDAO learningSequenceDAO;
    private UserSession userSession;
    private CycledMenuComponent cycledMenuComponent;
    private LearningView learningView;

    @Autowired
    public LearningSequenceSelectionView(final Navigator navigator,
                                         final IRenderer renderer,
                                         final LearningSequenceDAO learningSequenceDAO,
                                         final UserSession userSession,
                                         final CycledMenuComponent cycledMenuComponent,
                                         final LearningView learningView) {
        this.navigator = navigator;
        this.renderer = renderer;
        this.learningSequenceDAO = learningSequenceDAO;
        this.userSession = userSession;
        this.cycledMenuComponent = cycledMenuComponent;
        this.learningView = learningView;

    }

    @Override
    public void enter() {
        renderer.renderMessage(Messages.LEARNING_VIEW);
        NavigatorUtil.registerMainMenuNavigation(navigator);
        Set<LearningSequence> sequences = learningSequenceDAO.getLearningSequences(userSession.getUser().getUserName());

        List<LearningSequenceComponent> selection = sequences.stream().map(LearningSequenceComponent::new).collect(Collectors.toList());
        cycledMenuComponent.cycleThroughComponents(index -> startLearningWithSequence(selection.get(index)), selection);
    }

    private void startLearningWithSequence(final LearningSequenceComponent selectedComponent) {
        learningView.refresh(selectedComponent);
        navigator.navigate(learningView);
    }

    @Override
    public Messages getLabel() {
        return Messages.LEARNING_LABEL;
    }
}
