package com.trnka.trnkadevice.ui.learning;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.domain.LearningSequence;
import com.trnka.trnkadevice.domain.Sequence;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.repository.LearningSequenceRepository;
import com.trnka.trnkadevice.ui.CycledComponent;
import com.trnka.trnkadevice.ui.IView;
import com.trnka.trnkadevice.ui.MenuStudentView;
import com.trnka.trnkadevice.ui.SequenceComponent;
import com.trnka.trnkadevice.ui.UserSession;
import com.trnka.trnkadevice.ui.messages.AudioMessage;
import com.trnka.trnkadevice.ui.messages.Messages;
import com.trnka.trnkadevice.ui.navigation.Navigator;

import lombok.RequiredArgsConstructor;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequiredArgsConstructor
public class IndividualLearningMenuView implements IView {

    private final Navigator navigator;
    private final IRenderer renderer;
    private final LearningSequenceRepository learningSequenceRepository;
    private final UserSession userSession;
    private final CycledComponent cycledComponent;
    private final IndividualLearningView individualLearningView;

    @Override
    public void enter() {
        renderer.renderMessage(AudioMessage.of(Messages.LEARNING_VIEW_MENU));
        Set<LearningSequence> sequences = learningSequenceRepository.findAllLearningSequencesForUser(userSession.getUsername());

        List<SequenceComponent> selection = sequences.stream().map(SequenceComponent::new).collect(Collectors.toList());
        cycledComponent.cycleThroughComponents(index -> startLearningWithSequence(selection.get(index).getSequence()), selection);
    }

    @Override
    public Class<? extends IView> onEscape() {
        return MenuStudentView.class;
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
