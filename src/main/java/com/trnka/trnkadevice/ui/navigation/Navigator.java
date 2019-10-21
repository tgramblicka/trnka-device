package com.trnka.trnkadevice.ui.navigation;

import javax.inject.Singleton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.ui.IView;

@Component
@Singleton
public class Navigator {

    @Autowired
    private ApplicationContext context;

    public <T extends IView> void navigate(Class<T> viewClass) {
        T view = context.getBean(viewClass);
        view.enter();
    }

    public <T extends IView> void navigate(T view) {
        view.enter();
    }

}
