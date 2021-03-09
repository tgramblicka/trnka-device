package com.trnka.trnkadevice.ui.learning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.domain.MethodicalLearningSequence;
import com.trnka.trnkadevice.domain.UserPassedMethodicalSequence;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.repository.MethodicalLearningSequenceRepository;
import com.trnka.trnkadevice.repository.UserPassedMethodicsRepository;
import com.trnka.trnkadevice.ui.CycledComponent;
import com.trnka.trnkadevice.ui.IView;
import com.trnka.trnkadevice.ui.SequenceComponent;
import com.trnka.trnkadevice.ui.UserSession;
import com.trnka.trnkadevice.ui.messages.AudioMessage;
import com.trnka.trnkadevice.ui.messages.Messages;
import com.trnka.trnkadevice.ui.navigation.Navigator;

import lombok.RequiredArgsConstructor;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequiredArgsConstructor
public class LettersSelectionView implements IView {
    private final Navigator navigator;
    private final IRenderer renderer;
    private final MethodicalLearningSequenceRepository repo;
    private final UserSession userSession;
    private final CycledComponent cycledComponent;
    private final LettersLearningView lettersLearningView;
    private final UserPassedMethodicsRepository passedMethodicsRepository;


    @Override

    public void enter() {
        renderer.renderMessage(AudioMessage.of(Messages.LEARNING_LETTERS_SELECTION_VIEW));

        List<MethodicalLearningSequence> passedSequences =  passedMethodicsRepository.findAllById_UserId(userSession.getUserId()).stream().map(UserPassedMethodicalSequence::getSequence).collect(Collectors.toList());
        Collections.sort(passedSequences, Comparator.comparing(MethodicalLearningSequence:: getLevel));


        Integer highestPassedSequenceLevel = 0;
        if (!passedSequences.isEmpty()) {
            highestPassedSequenceLevel = passedSequences.stream().max(Comparator.comparingInt(MethodicalLearningSequence:: getLevel))
                    .map(MethodicalLearningSequence:: getLevel).get();
        }
        Optional<MethodicalLearningSequence> notPassedSequence = getNthSequence(highestPassedSequenceLevel + 1);
        if (notPassedSequence.isPresent()) {
            passedSequences.add(notPassedSequence.get());
        }
        List<MethodicalLearningSequence> sequenceList = passedSequences.stream().collect(Collectors.toList());
        Collections.sort(sequenceList, Comparator.comparingInt(MethodicalLearningSequence:: getLevel));

        List<SequenceComponent> selection = new ArrayList<>();
        selection.addAll(passedSequences.stream().map(SequenceComponent::new).collect(Collectors.toList()));
        cycledComponent.cycleThroughComponents(index -> startLearningWithSequence(selection.get(index)), selection);
    }

    @Override
    public Class<? extends IView> onEscape() {
        return MethodicalLearningMenuView.class;
    }

    private Optional<MethodicalLearningSequence> getNthSequence(final Integer n) {
        return repo.findByLevel(n);
    }

    private void startLearningWithSequence(final SequenceComponent selectedComponent) {
        lettersLearningView.refresh(selectedComponent.getSequence().getId());
        navigator.navigateAsync(lettersLearningView.getClass());
    }


    @Override
    public Messages getMessage() {
        return Messages.LEARNING_LETTERS_SELECTION_MENU;
    }

    @Override
public List<Messages> getParams() {
        return Collections.emptyList();
    }
}
