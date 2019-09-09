package com.trnka.trnkadevice.ui;

import com.trnka.trnkadevice.inputreader.DeviceInputReader;
import com.trnka.trnkadevice.inputreader.Keystroke;
import com.trnka.trnkadevice.renderer.IRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CycledMenuComponent {

    private IRenderer renderer;
    private Navigator navigator;

    @Autowired
    private ApplicationContext context;

    @Autowired
    public CycledMenuComponent(final IRenderer renderer,
                               final Navigator navigator) {
        this.renderer = renderer;
        this.navigator = navigator;
    }

    public void cycleThroughMenu(final List<Class<? extends IView>> menu) {
        Keystroke key = DeviceInputReader.readKey();
        int index = 0;
        while (true) {
            switch (key) {
            case UP:
                index = (index + 1) % menu.size();
                renderViewName(menu, index);
                break;
            case DOWN:
                index = (index - 1) % menu.size();
                index = index < 0 ? menu.size() - 1
                                  : index;
                renderViewName(menu, index);
                break;
            case SUBMIT:
                navigator.navigate(menu.get(index));
                return;
            }
            key = DeviceInputReader.readKey();
        }
    }

    private void renderViewName(final List<Class<? extends IView>> menu,
                                final int index) {
        Class<? extends IView> viewClass = menu.get(index);
        renderer.renderMessage(context.getBean(viewClass).getViewName());
    }
}
