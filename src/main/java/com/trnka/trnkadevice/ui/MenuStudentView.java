package com.trnka.trnkadevice.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.ui.learning.IndividualLearningMenuView;
import com.trnka.trnkadevice.ui.learning.MethodicalLearningMenuView;
import com.trnka.trnkadevice.ui.messages.AudioMessage;
import com.trnka.trnkadevice.ui.messages.Messages;
import com.trnka.trnkadevice.ui.navigation.Navigator;
import com.trnka.trnkadevice.ui.testing.IndividualTestingMenuView;

@Component
public class MenuStudentView implements IView {

    private IRenderer renderer;
    private UserSession userSession;
    private Navigator navigator;
    private CycledComponent cycledComponent;

    private static List<Class<? extends IView>> MENU = new ArrayList<>();

    static {
        MENU.add(MethodicalLearningMenuView.class);
        MENU.add(IndividualLearningMenuView.class);
        MENU.add(IndividualTestingMenuView.class);
//        MENU.add(ResultsSelectionView.class);
        MENU.add(LogoutView.class);
    }

    @Autowired
    public MenuStudentView(final IRenderer renderer,
                           final UserSession userSession,
                           final Navigator navigator,
                           final CycledComponent cycledComponent) {
        this.renderer = renderer;
        this.userSession = userSession;
        this.navigator = navigator;
        this.cycledComponent = cycledComponent;
    }

    @Override
    public void enter() {
        if (userSession.getUserId() == null) {
            navigator.navigateAsync(LoginView.class);
            return;
        }
        renderer.renderMessage(AudioMessage.of(Messages.MAIN_MENU));
        Consumer<Integer> consumer = index -> navigator.navigateAsync(MENU.get(index));
        cycledComponent.cycleThroughMenu(consumer, MENU.toArray(new Class[MENU.size()]));
    }

    @Override public IView onEscape() {
        return null;
    }

    @Override
    public Messages getMessage() {
        return Messages.MAIN_MENU_LABEL;
    }

    @Override
public List<Messages> getParams() {
        return Collections.emptyList();
    }

}
