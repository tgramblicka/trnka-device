package com.trnka.trnkadevice.ui.learning;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.trnka.trnkadevice.domain.Sequence;
import com.trnka.trnkadevice.ui.SequenceComponent;
import com.trnka.trnkadevice.ui.messages.AudioMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.dao.LearningSequenceDAO;
import com.trnka.trnkadevice.domain.LearningSequence;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.ui.CycledComponent;
import com.trnka.trnkadevice.ui.IView;
import com.trnka.trnkadevice.ui.UserSession;
import com.trnka.trnkadevice.ui.messages.Messages;
import com.trnka.trnkadevice.ui.navigation.Navigator;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
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
        renderer.renderMessage(AudioMessage.of(Messages.LEARNING_VIEW_MENU));
        Set<LearningSequence> sequences = learningSequenceDAO.getLearningSequences(userSession.getUsername());

        List<SequenceComponent> selection = sequences.stream().map(SequenceComponent::new).collect(Collectors.toList());
        cycledComponent.cycleThroughComponents(index -> startLearningWithSequence(selection.get(index).getSequence()), selection);
    }

    @Override public IView onEscape() {
        return null;
    }

    private void startLearningWithSequence(final Sequence sequence) {
        individualLearningView.refresh(sequence.getId());
        navigator.navigateAsync(IndividualLearningView.class);
    }

    @Override
    public Messages getMessage() {
        return Messages.LEARNING_LABEL_MENU;
    }

    @Override
    public List<Messages> getParams() {
        return Collections.emptyList();
    }
}
