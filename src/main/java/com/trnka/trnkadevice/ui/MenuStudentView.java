package com.trnka.trnkadevice.ui;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.ui.learning.LearningView;
import com.trnka.trnkadevice.ui.messages.Messages;

@Component
public class MenuStudentView implements IView {

    private IRenderer renderer;
    private UserSession userSession;
    private Navigator navigator;
    private CycledMenuComponent cycledMenuComponent;
    @Autowired
    private ApplicationContext context;

    private static List<Class<? extends IView>> MENU = new ArrayList<>();

    static {
        MENU.add(LearningView.class);
        MENU.add(TestingView.class);
        MENU.add(ResultsView.class);
    }

    @Autowired
    public MenuStudentView(final IRenderer renderer,
                           final UserSession userSession,
                           final Navigator navigator,
                           final CycledMenuComponent cycledMenuComponent) {
        this.renderer = renderer;
        this.userSession = userSession;
        this.navigator = navigator;
        this.cycledMenuComponent = cycledMenuComponent;
    }

    @Override
    public void enter() {
        renderer.renderMessage(Messages.MAIN_MENU, userSession.getUser().getUserName());
        cycledMenuComponent.cycleThroughMenu(MENU, index -> navigator.navigate(MENU.get(index)));
    }

    @Override
    public Messages getViewName() {
        return Messages.MAIN_MENU_LABEL;
    }

}
