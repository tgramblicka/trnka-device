package com.trnka.trnkadevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;

import com.trnka.trnkadevice.ui.LoginView;
import org.springframework.stereotype.Service;

@Service
public class AsyncGateway {

    @Autowired
    private ApplicationContext context;


    @Async
    public void startAsync() {
        LoginView view = context.getBean(LoginView.class);
        view.enter();
    }
}
