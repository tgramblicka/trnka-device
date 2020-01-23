package com.trnka.trnkadevice.ui.learning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.trnka.trnkadevice.domain.MethodicalLearningSequence;
import com.trnka.trnkadevice.ui.SequenceComponent;
import org.springframework.beans.factory.annotation.Autowired;

import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.repository.MethodicalLearningSequenceRepository;
import com.trnka.trnkadevice.ui.CycledComponent;
import com.trnka.trnkadevice.ui.IView;
import com.trnka.trnkadevice.ui.UserSession;
import com.trnka.trnkadevice.ui.messages.Messages;
import com.trnka.trnkadevice.ui.navigation.Navigator;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class LettersView implements IView {
    private Navigator navigator;
    private IRenderer renderer;
    private MethodicalLearningSequenceRepository repo;
    private UserSession userSession;
    private CycledComponent cycledComponent;
    private MethodicalLearningView methodicalLearningView;

    @Autowired
    public LettersView(final Navigator navigator,
                       final IRenderer renderer,
                       final MethodicalLearningSequenceRepository repo,
                       final UserSession userSession,
                       final CycledComponent cycledComponent,
                       final MethodicalLearningView methodicalLearningView) {
        this.navigator = navigator;
        this.renderer = renderer;
        this.repo = repo;
        this.userSession = userSession;
        this.cycledComponent = cycledComponent;
        this.methodicalLearningView = methodicalLearningView;
    }

    @Override

    public void enter() {
        renderer.renderMessage(Messages.LEARNING_LETTERS_VIEW);

        Set<MethodicalLearningSequence> passedSequences = userSession.getUser().get().getPassedSequences();

        Integer highestPassedSequenceOrder = 0;
        if (!passedSequences.isEmpty()) {
            highestPassedSequenceOrder = passedSequences.stream().max(Comparator.comparingInt(MethodicalLearningSequence::getOrder))
                    .map(MethodicalLearningSequence::getOrder).get();
        }
        Optional<MethodicalLearningSequence> notPassedSequence = getNthSequence(highestPassedSequenceOrder + 1);
        if (notPassedSequence.isPresent()) {
            passedSequences.add(notPassedSequence.get());
        }
        List<MethodicalLearningSequence> sequenceList = passedSequences.stream().collect(Collectors.toList());
        Collections.sort(sequenceList, Comparator.comparingInt(MethodicalLearningSequence::getOrder));

        List<SequenceComponent> selection = new ArrayList<>();
        selection.addAll(passedSequences.stream().map(SequenceComponent::new).collect(Collectors.toList()));
        cycledComponent.cycleThroughComponents(index -> startLearningWithSequence(selection.get(index)), selection);
    }

    private Optional<MethodicalLearningSequence> getNthSequence(final Integer n) {
        return repo.findByOrder(n);
    }

    private void startLearningWithSequence(final SequenceComponent selectedComponent) {
        methodicalLearningView.refresh(selectedComponent.getSequence().getId());
        navigator.navigateAsync(methodicalLearningView.getClass());
    }


    @Override
    public Messages getLabel() {
        return Messages.LEARNING_LETTERS_MENU;
    }

    @Override
    public List<String> getMessageParams() {
        return Collections.emptyList();
    }
}
