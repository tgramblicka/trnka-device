package com.trnka.trnkadevice.ui;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trnka.restapi.dto.UserDTO;
import com.trnka.trnkadevice.controller.RestClientBackend;
import com.trnka.trnkadevice.inputreader.InputReader;
import com.trnka.trnkadevice.inputreader.Keystroke;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.ui.messages.Messages;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoginView implements IView {
    public static final String TEST_PASSWORD = "aaaa";
    private static final int PASSWORD_LENGHT = 4;

    private IRenderer renderer;
    private RestClientBackend restClientBackend;
    private UserSession userSession;
    private Navigator navigator;
    private InputReader inputReader;

    @Autowired
    public LoginView(IRenderer renderer,
                     RestClientBackend restClientBackend,
                     UserSession userSession,
                     Navigator navigator,
                     InputReader inputReader) {
        this.renderer = renderer;
        this.restClientBackend = restClientBackend;
        this.userSession = userSession;
        this.navigator = navigator;
        this.inputReader = inputReader;
    }

    @Override
    public void enter() {
        log.info("Entering login UI");
        renderer.renderMessage(Messages.TYPE_IN_YOUR_PASSWORD);
        String password = readPassword();
        Optional<UserDTO> userDTO = restClientBackend.authenticate(password);
        if (userDTO.isPresent()) {
            userSession.setUser(userDTO.get());
            renderer.renderMessage(Messages.YOU_HAVE_BEEN_SUCCESSFULY_LOGGED_IN);
            navigator.navigate(MenuStudentView.class);
            return;
        }
        renderer.renderMessage(Messages.WRONG_PASSWORD);
        navigator.navigate(LoginView.class);
    }

    private String readPassword1() {
        return TEST_PASSWORD;
    }

    private String readPassword() {
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
}
