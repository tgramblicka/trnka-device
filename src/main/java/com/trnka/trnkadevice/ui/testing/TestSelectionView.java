package com.trnka.trnkadevice.ui.testing;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.dao.TestingSequenceDAO;
import com.trnka.trnkadevice.domain.TestingSequence;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.ui.CycledComponent;
import com.trnka.trnkadevice.ui.IView;
import com.trnka.trnkadevice.ui.SequenceComponent;
import com.trnka.trnkadevice.ui.UserSession;
import com.trnka.trnkadevice.ui.messages.Messages;
import com.trnka.trnkadevice.ui.navigation.Navigator;

@Component
public class TestSelectionView implements IView {

    private IRenderer renderer;
    private Navigator navigator;
    private UserSession userSession;
    private IndividualTestingView individualTestingView;
    private TestingSequenceDAO testingSequenceDao;
    private CycledComponent cycledComponent;

    @Autowired
    public TestSelectionView(final Navigator navigator,
                             final IRenderer renderer,
                             final UserSession userSession,
                             final CycledComponent cycledComponent,
                             final TestingSequenceDAO testingSequenceDao,
                             final IndividualTestingView individualTestingView) {
        this.navigator = navigator;
        this.renderer = renderer;
        this.testingSequenceDao = testingSequenceDao;
        this.userSession = userSession;
        this.cycledComponent = cycledComponent;
        this.individualTestingView = individualTestingView;
    }

    @Override
    public void enter() {
        renderer.renderMessage(Messages.TESTING_VIEW);
        Set<TestingSequence> sequences = testingSequenceDao.getSequences(userSession.getUsername());
        List<SequenceComponent> selection = sequences.stream().map(SequenceComponent::new).collect(Collectors.toList());
        cycledComponent.cycleThroughComponents(index -> startTestingWithSequence(selection.get(index)), selection);
    }

    private void startTestingWithSequence(final SequenceComponent selectedComponent) {
        individualTestingView.refresh(selectedComponent);
        navigator.navigate(individualTestingView);
    }

    @Override
    public Messages getLabel() {
        return Messages.TESTING_LABEL;
    }

    @Override
    public List<String> getMessageParams() {
        return Collections.emptyList();
    }

}
