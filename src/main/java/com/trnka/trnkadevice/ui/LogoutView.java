package com.trnka.trnkadevice.ui;

import com.trnka.trnkadevice.renderer.IRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.ui.messages.Messages;
import com.trnka.trnkadevice.ui.navigation.Navigator;

import java.util.Collections;
import java.util.List;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class LogoutView implements IView {

    private Navigator navigator;
    private UserSession userSession;
    private IRenderer renderer;

    @Autowired
    public LogoutView(final Navigator navigator,
                      final UserSession userSession,
                      final IRenderer renderer) {
        this.navigator = navigator;
        this.userSession = userSession;
        this.renderer = renderer;
    }

    @Override
    public void enter() {
        renderer.renderMessage(Messages.LOGGING_OUT);
        userSession.logout();
        navigator.navigateAsync(LoginView.class);
    }

    @Override
    public Messages getLabel() {
        return Messages.LOG_OUT;
    }

    @Override
    public List<String> getMessageParams() {
        return Collections.emptyList();
    }

}
