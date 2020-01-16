package com.trnka.trnkadevice.ui.navigation;

import java.util.EventListener;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.trnka.trnkadevice.ui.IView;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class Navigator implements EventListener {

    @Autowired
    private ApplicationContext context;
    private EventBus eventBus;
    private Class<?> currentView;

    @PostConstruct
    public void init() {
        eventBus = new EventBus();
        eventBus.register(this);
    }

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

    public <T extends IView> void navigateAsync(Class<T> viewClass) {
        eventBus.post(new NavigationEvent(viewClass,
                                          currentView));
    }

    public void navigateAsync(IView view) {
        ViewNavigationEvent ev = new ViewNavigationEvent();
        ev.setDestinationView(view);
        ev.setOriginView(view);
        eventBus.post(ev);
    }

    @Subscribe
    public <T extends IView> void onNavigationEvent(NavigationEvent<T> navigationEvent) {
        navigate(navigationEvent.getDestinationViewClass());
    }

}
