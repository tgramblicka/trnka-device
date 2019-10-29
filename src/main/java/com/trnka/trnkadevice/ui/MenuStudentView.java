package com.trnka.trnkadevice.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.ui.learning.LearningSequenceSelectionView;
import com.trnka.trnkadevice.ui.messages.Messages;
import com.trnka.trnkadevice.ui.navigation.Navigator;
import com.trnka.trnkadevice.ui.testing.TestSelectionView;

@Component
public class MenuStudentView implements IView {

    private IRenderer renderer;
    private UserSession userSession;
    private Navigator navigator;
    private CycledComponent cycledComponent;

    private static List<Class<? extends IView>> MENU = new ArrayList<>();

    static {
        MENU.add(LearningSequenceSelectionView.class);
        MENU.add(TestSelectionView.class);
        MENU.add(ResultsView.class);
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
        if (userSession.getUser() == null) {
            navigator.navigate(LoginView.class);
            return;
        }
        renderer.renderMessage(Messages.MAIN_MENU, userSession.getUser().getUserName());
        Consumer<Integer> consumer = index -> navigator.navigate(MENU.get(index));
        cycledComponent.cycleThroughMenu(consumer, MENU.toArray(new Class[MENU.size()]));
    }

    @Override
    public Messages getLabel() {
        return Messages.MAIN_MENU_LABEL;
    }
}
