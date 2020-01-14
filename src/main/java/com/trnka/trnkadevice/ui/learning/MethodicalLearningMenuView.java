package com.trnka.trnkadevice.ui.learning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.TransactionalUtil;
import com.trnka.trnkadevice.domain.MethodicalLearningSequence;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.repository.MethodicalLearningSequenceRepository;
import com.trnka.trnkadevice.ui.CycledComponent;
import com.trnka.trnkadevice.ui.IView;
import com.trnka.trnkadevice.ui.SequenceComponent;
import com.trnka.trnkadevice.ui.UserSession;
import com.trnka.trnkadevice.ui.messages.Messages;
import com.trnka.trnkadevice.ui.navigation.Navigator;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MethodicalLearningMenuView implements IView {

    private Navigator navigator;
    private IRenderer renderer;
    private MethodicalLearningSequenceRepository repo;
    private UserSession userSession;
    private CycledComponent cycledComponent;
    private MethodicalLearningView methodicalLearningView;

    @Autowired
    private TransactionalUtil transactionalUtil;

    @Autowired
    public MethodicalLearningMenuView(final Navigator navigator,
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
        renderer.renderMessage(Messages.LEARNING_VIEW_MENU);

        List<SequenceComponent> selection = new ArrayList<>();
        Set<MethodicalLearningSequence> sequences = userSession.getUser().get().getPassedSequences();
        List<MethodicalLearningSequence> sequenceList = sequences.stream().collect(Collectors.toList());

        if (sequences.isEmpty()) {
            sequences.add(getFirstSequence());
        }
        Integer highestPassedSequenceOrder = sequences.stream().max(Comparator.comparingInt(MethodicalLearningSequence::getOrder))
                .map(MethodicalLearningSequence::getOrder).get();
        Optional<MethodicalLearningSequence> notPassedSequence = getNthSequence(highestPassedSequenceOrder+1);
        if (notPassedSequence.isPresent()) {
            sequences.add(notPassedSequence.get());
        }
        Collections.sort(sequenceList, Comparator.comparingInt(MethodicalLearningSequence::getOrder));
        selection.addAll(sequences.stream().map(SequenceComponent::new).collect(Collectors.toList()));
        cycledComponent.cycleThroughComponents(index -> startLearningWithSequence(selection.get(index)), selection);
    }

    private Optional<MethodicalLearningSequence> getNthSequence(final Integer n) {
        return repo.findByOrder(n);
    }

    private MethodicalLearningSequence getFirstSequence() {
        Optional<MethodicalLearningSequence> seq = repo.findByOrder(1);
        if (seq.isPresent()) {
            return seq.get();
        } else {
            log.error("Very first methodical sequence not found!");
            throw new NoResultException("Very first methodical sequence not found!");
        }
    }

    private void startLearningWithSequence(final SequenceComponent selectedComponent) {
        methodicalLearningView.refresh(selectedComponent.getSequence().getId());
        navigator.navigateAsync(methodicalLearningView.getClass());
    }

    @Override
    public Messages getLabel() {
        return Messages.METHODICAL_LEARNING_MENU;
    }

    @Override
    public List<String> getMessageParams() {
        return Collections.emptyList();
    }

}
