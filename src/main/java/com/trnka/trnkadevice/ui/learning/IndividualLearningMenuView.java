package com.trnka.trnkadevice.ui.learning;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.trnka.trnkadevice.domain.Sequence;
import com.trnka.trnkadevice.ui.SequenceComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.dao.LearningSequenceDAO;
import com.trnka.trnkadevice.domain.LearningSequence;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.ui.CycledComponent;
import com.trnka.trnkadevice.ui.IView;
import com.trnka.trnkadevice.ui.UserSession;
import com.trnka.trnkadevice.ui.messages.Messages;
import com.trnka.trnkadevice.ui.navigation.Navigator;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(propagation =  Propagation.REQUIRES_NEW)
public class IndividualLearningMenuView implements IView {

    private Navigator navigator;
    private IRenderer renderer;
    private LearningSequenceDAO learningSequenceDAO;
    private UserSession userSession;
    private CycledComponent cycledComponent;
    private IndividualLearningView individualLearningView;

    @Autowired
    public IndividualLearningMenuView(final Navigator navigator,
                                      final IRenderer renderer,
                                      final LearningSequenceDAO learningSequenceDAO,
                                      final UserSession userSession,
                                      final CycledComponent cycledComponent,
                                      final IndividualLearningView individualLearningView) {
        this.navigator = navigator;
        this.renderer = renderer;
        this.learningSequenceDAO = learningSequenceDAO;
        this.userSession = userSession;
        this.cycledComponent = cycledComponent;
        this.individualLearningView = individualLearningView;
    }

    @Override
    public void enter() {
        renderer.renderMessage(Messages.LEARNING_VIEW_MENU);
        Set<LearningSequence> sequences = learningSequenceDAO.getLearningSequences(userSession.getUsername());

        List<SequenceComponent> selection = sequences.stream().map(SequenceComponent::new).collect(Collectors.toList());
        cycledComponent.cycleThroughComponents(index -> startLearningWithSequence(selection.get(index).getSequence()), selection);
    }

    private void startLearningWithSequence(final Sequence sequence) {
        individualLearningView.refresh(sequence.getId());
        navigator.navigate(individualLearningView);
    }

    @Override
    public Messages getLabel() {
        return Messages.LEARNING_LABEL;
    }

    @Override public List<String> getMessageParams() {
        return Collections.emptyList();
    }
}
