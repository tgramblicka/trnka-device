package com.trnka.trnkadevice.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;

@Component
@Singleton
public class Navigator {

    @Autowired
    private ApplicationContext context;

    public <T extends IView> void navigate(Class<T> viewClass) {
        T view = context.getBean(viewClass);
        view.enter();
    }
}
