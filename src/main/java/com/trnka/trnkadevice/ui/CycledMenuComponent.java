package com.trnka.trnkadevice.ui;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.inputreader.InputReader;
import com.trnka.trnkadevice.inputreader.Keystroke;
import com.trnka.trnkadevice.renderer.IRenderer;

@Component
public class CycledMenuComponent {

    private IRenderer renderer;
    private InputReader inputReader;

    @Autowired
    private ApplicationContext context;

    @Autowired
    public CycledMenuComponent(final IRenderer renderer,
                               final InputReader inputReader) {
        this.renderer = renderer;
        this.inputReader = inputReader;
    }

    public void cycleThroughMenu(final List<Class<? extends IView>> menu,
                                 final Consumer<Integer> onSubmit) {
        Keystroke key = inputReader.readFromInput();
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
                onSubmit.accept(index);
                return;
            }
            key = inputReader.readFromInput();
        }
    }

    private void renderViewName(final List<Class<? extends IView>> menu,
                                final int index) {
        Class<? extends IView> viewClass = menu.get(index);
        renderer.renderMessage(context.getBean(viewClass).getViewName());
    }
}
