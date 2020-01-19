package com.trnka.trnkadevice.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.Authentication;
import com.trnka.trnkadevice.inputreader.Keystroke;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.ui.interaction.UserInteraction;
import com.trnka.trnkadevice.ui.interaction.UserInteractionHandler;
import com.trnka.trnkadevice.ui.messages.Messages;
import com.trnka.trnkadevice.ui.navigation.Navigator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class LoginView implements IView {
    public static final String TEST_PASSWORD = "aaaa";
    private static final int PASSWORD_LENGHT = 4;

    private IRenderer renderer;
    private Navigator navigator;
    private UserInteractionHandler userInteractionHandler;
    private Authentication authentication;

    @Autowired
    public LoginView(IRenderer renderer,
                     Navigator navigator,
                     UserInteractionHandler userInteractionHandler,
                     Authentication authentication) {
        this.renderer = renderer;
        this.navigator = navigator;
        this.userInteractionHandler = userInteractionHandler;
        this.authentication = authentication;
    }

    @Override
    public void enter() {
        log.info("Entering login UI");
        renderer.renderMessage(Messages.TYPE_IN_YOUR_PASSWORD);
        String code = readLoginCode();
        Boolean authenticated = authentication.authenticate(code);
        if (authenticated) {
            renderer.renderMessage(Messages.YOU_HAVE_BEEN_SUCCESSFULY_LOGGED_IN);
            navigator.navigateAsync(MenuStudentView.class);
            return;
        }
        renderer.renderMessage(Messages.WRONG_PASSWORD);
        navigator.navigateAsync(LoginView.class);
    }

    private String readLoginCode() {
        List<Keystroke> keyStrokes = new ArrayList<>();
        UserInteraction userInteraction = userInteractionHandler.readUserInteraction();

        while (!userInteraction.getKeystroke().equals(Keystroke.SUBMIT)) {
            keyStrokes.add(userInteraction.getKeystroke());
            userInteraction = userInteractionHandler.readUserInteraction();
        }
        return keyStrokes.stream().map(Keystroke::getValue).collect(Collectors.joining(""));
    }

    @Override
    public Messages getLabel() {
        return Messages.LOGIN_VIEW_LABEL;
    }

    @Override
    public List<String> getMessageParams() {
        return Collections.emptyList();
    }

}
