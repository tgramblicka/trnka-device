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
    private Class<?> currentView;

    public <T extends IView> void navigate(Class<T> viewClass) {
        T view = context.getBean(viewClass);
        navigate(view);
    }

    public <T extends IView> void navigate(T view) {
        view.enter();
        setCurrentView(view.getClass());
    }

    public Class<?> getCurrentView() {
        return currentView;
    }

    public void setCurrentView(final Class<?> currentView) {
        this.currentView = currentView;
    }
}
