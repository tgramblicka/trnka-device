package com.trnka.trnkadevice.ui;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.inputreader.DeviceInputReader;
import com.trnka.trnkadevice.inputreader.Keystroke;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.ui.messages.Messages;

@Component
public class MenuStudentView implements IView {

    private IRenderer renderer;
    private UserSession userSession;
    private Navigator navigator;

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
                           final Navigator navigator) {
        this.renderer = renderer;
        this.userSession = userSession;
        this.navigator = navigator;
    }

    @Override
    public void enter() {
        renderer.renderMessage(Messages.MAIN_MENU, userSession.getUser().getUserName());

        Keystroke key = DeviceInputReader.readKey();
        int index = 0;
        while (true) {
            switch (key) {
            case UP:
                index = (index + 1) % MENU.size();
                renderViewName(index);
                break;
            case DOWN:
                index = (index - 1) % MENU.size();
                index = index < 0 ? MENU.size() - 1
                                  : index;
                renderViewName(index);
                break;
            case SUBMIT:
                navigator.navigate(MENU.get(index));
                return;
            }
            key = DeviceInputReader.readKey();
        }
    }

    private void renderViewName(final int index) {
        Class<? extends IView> viewClass = MENU.get(index);
        renderer.renderMessage(context.getBean(viewClass).getViewName());
    }

    @Override
    public Messages getViewName() {
        return Messages.MAIN_MENU_LABEL;
    }

}
