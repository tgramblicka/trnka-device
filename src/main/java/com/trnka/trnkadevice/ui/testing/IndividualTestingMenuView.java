package com.trnka.trnkadevice.ui.testing;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.domain.TestingSequence;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.repository.TestingSequenceRepository;
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
public class IndividualTestingMenuView implements IView {

    private final IRenderer renderer;
    private final Navigator navigator;
    private final UserSession userSession;
    private final IndividualTestingView individualTestingView;
    private final TestingSequenceRepository testingSequenceRepository;
    private final CycledComponent cycledComponent;


    @Override
    public void enter() {
        renderer.renderMessage(AudioMessage.of(Messages.TESTING_VIEW));
        Set<TestingSequence> sequences = testingSequenceRepository.findAllTestingSequencesForUser(userSession.getUsername());
        List<SequenceComponent> selection = sequences.stream().map(SequenceComponent::new).collect(Collectors.toList());
        cycledComponent.cycleThroughComponents(index -> startTestingWithSequence(selection.get(index)), selection);
    }

    @Override
    public Class<? extends IView> onEscape() {
        return MenuStudentView.class;
    }

    private void startTestingWithSequence(final SequenceComponent selectedComponent) {
        individualTestingView.refresh(selectedComponent);
        navigator.navigateAsync(individualTestingView.getClass());
    }

    @Override
    public Messages getMessage() {
        return Messages.TESTING_LETTERS_LABEL_MENU;
    }

    @Override
public List<Messages> getParams() {
        return Collections.emptyList();
    }

}
