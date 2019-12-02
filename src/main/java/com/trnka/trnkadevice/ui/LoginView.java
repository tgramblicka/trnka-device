package com.trnka.trnkadevice.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.Authentication;
import com.trnka.trnkadevice.controller.RestClientBackend;
import com.trnka.trnkadevice.inputreader.InputReader;
import com.trnka.trnkadevice.inputreader.Keystroke;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.ui.messages.Messages;
import com.trnka.trnkadevice.ui.navigation.Navigator;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class LoginView implements IView {
    public static final String TEST_PASSWORD = "aaaa";
    private static final int PASSWORD_LENGHT = 4;

    private IRenderer renderer;
    private RestClientBackend restClientBackend;
    private Navigator navigator;
    private InputReader inputReader;
    private Authentication authentication;

    @Autowired
    public LoginView(IRenderer renderer,
                     RestClientBackend restClientBackend,
                     UserSession userSession,
                     Navigator navigator,
                     InputReader inputReader,
                     Authentication authentication) {
        this.renderer = renderer;
        this.restClientBackend = restClientBackend;
        this.navigator = navigator;
        this.inputReader = inputReader;
        this.authentication = authentication;
    }

    @Override
    public void enter() {
        log.info("Entering login UI");
        renderer.renderMessage(Messages.TYPE_IN_YOUR_PASSWORD);
        String code = readCode();
        Boolean authenticated = authentication.authenticate(code);
        if (authenticated) {
            renderer.renderMessage(Messages.YOU_HAVE_BEEN_SUCCESSFULY_LOGGED_IN);
            navigator.navigate(MenuStudentView.class);
            return;
        }
        renderer.renderMessage(Messages.WRONG_PASSWORD);
        navigator.navigate(LoginView.class);
    }

    private String readCode() {
        StringBuilder builder = new StringBuilder("");
        for (int i = 0; i < PASSWORD_LENGHT; i++) {
            Keystroke key = inputReader.readFromInput();
            builder.append(key.getValue());
        }
        return builder.toString();
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
