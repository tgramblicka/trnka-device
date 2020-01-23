package com.trnka.trnkadevice.ui.learning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.repository.MethodicalLearningSequenceRepository;
import com.trnka.trnkadevice.ui.CycledComponent;
import com.trnka.trnkadevice.ui.IView;
import com.trnka.trnkadevice.ui.UserSession;
import com.trnka.trnkadevice.ui.messages.Messages;
import com.trnka.trnkadevice.ui.navigation.Navigator;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class MethodicalLearningMenuView implements IView {
    private static List<Class<? extends IView>> MENU = new ArrayList<>();

    static {
        MENU.add(LettersView.class);
        MENU.add(SyllablesView.class);
        MENU.add(WordsView.class);
        MENU.add(SentencesView.class);


    }

    private Navigator navigator;
    private IRenderer renderer;
    private MethodicalLearningSequenceRepository repo;
    private UserSession userSession;
    private CycledComponent cycledComponent;
    private MethodicalLearningView methodicalLearningView;

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
        renderer.renderMessage(Messages.METHODICAL_LEARNING_MENU_VIEW);
        renderer.renderMessage(Messages.MAIN_MENU, userSession.getUsername());
        Consumer<Integer> consumer = index -> navigator.navigateAsync(MENU.get(index));
        cycledComponent.cycleThroughMenu(consumer, MENU.toArray(new Class[MENU.size()]));
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
