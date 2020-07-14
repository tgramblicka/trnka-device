package com.trnka.trnkadevice.ui.testing;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.trnka.trnkadevice.ui.messages.AudioMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
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
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class IndividualTestingMenuView implements IView {

    private IRenderer renderer;
    private Navigator navigator;
    private UserSession userSession;
    private IndividualTestingView individualTestingView;
    private TestingSequenceDAO testingSequenceDao;
    private CycledComponent cycledComponent;

    @Autowired
    public IndividualTestingMenuView(final Navigator navigator,
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
        renderer.renderMessage(AudioMessage.of(Messages.TESTING_VIEW));
        Set<TestingSequence> sequences = testingSequenceDao.getSequences(userSession.getUsername());
        List<SequenceComponent> selection = sequences.stream().map(SequenceComponent::new).collect(Collectors.toList());
        cycledComponent.cycleThroughComponents(index -> startTestingWithSequence(selection.get(index)), selection);
    }

    @Override public IView onEscape() {
        return null;
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
