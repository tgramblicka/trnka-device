package com.trnka.trnkadevice.ui.learning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import com.trnka.trnkadevice.ui.MenuStudentView;
import com.trnka.trnkadevice.ui.messages.AudioMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.ui.CycledComponent;
import com.trnka.trnkadevice.ui.IView;
import com.trnka.trnkadevice.ui.messages.Messages;
import com.trnka.trnkadevice.ui.navigation.Navigator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class MethodicalLearningMenuView implements IView {
    private static List<Class<? extends IView>> MENU = new ArrayList<>();

    static {
        MENU.add(LettersSelectionView.class);
        MENU.add(SyllablesSelectionView.class);
        MENU.add(WordsSelectionView.class);
        MENU.add(SentencesSelectionView.class);


    }

    private Navigator navigator;
    private IRenderer renderer;
    private CycledComponent cycledComponent;

    @Autowired
    public MethodicalLearningMenuView(final Navigator navigator,
                                      final IRenderer renderer,
                                      final CycledComponent cycledComponent) {
        this.navigator = navigator;
        this.renderer = renderer;
        this.cycledComponent = cycledComponent;
    }

    @Override
    @Transactional
    public void enter() {
        renderer.renderMessage(AudioMessage.of(Messages.METHODICAL_LEARNING_MENU_VIEW));
        Consumer<Integer> consumer = index -> navigator.navigateAsync(MENU.get(index));
        cycledComponent.cycleThroughMenu(consumer, MENU);
    }

    @Override
    public Class<? extends IView> onEscape() {
        return MenuStudentView.class;
    }

    @Override
    public Messages getMessage() {
        return Messages.METHODICAL_LEARNING_MENU;
    }

    @Override
public List<Messages> getParams() {
        return Collections.emptyList();
    }

}
