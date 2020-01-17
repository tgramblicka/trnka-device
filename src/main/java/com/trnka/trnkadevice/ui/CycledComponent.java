package com.trnka.trnkadevice.ui;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.inputreader.InputReader;
import com.trnka.trnkadevice.inputreader.Keystroke;
import com.trnka.trnkadevice.renderer.IRenderer;

@Component
public class CycledComponent {

    private IRenderer renderer;
    private InputReader inputReader;

    @Autowired
    private ApplicationContext context;

    @Autowired
    public CycledComponent(final IRenderer renderer,
                           final InputReader inputReader) {
        this.renderer = renderer;
        this.inputReader = inputReader;
    }

    public void cycleThroughComponents(final Consumer<Integer> onSubmit,
                                       final List<? extends Renderable> list) {
        Keystroke key = inputReader.readFromInput();
        int index = 0;
        while (true) {
            switch (key) {
            case UP:
                index = (index + 1) % list.size();
                renderer.renderLabel(list.get(index));
                break;
            case DOWN:
                index = (index - 1) % list.size();
                index = index < 0 ? list.size() - 1
                                  : index;
                renderer.renderLabel(list.get(index));
                break;
            case MENU_1:
                return;
            case SUBMIT:
                onSubmit.accept(index);
                return;
            }
            key = inputReader.readFromInput();
        }
    }

    public <E extends Renderable> void cycleThroughMenu(final Consumer<Integer> onSubmit,
                                                        final Class<E>... renderables) {
        List<Class<E>> list = Stream.of(renderables).collect(Collectors.toList());
        Keystroke key = inputReader.readFromInput();
        int index = 0;
        while (true) {
            switch (key) {
            case UP:
                index = (index + 1) % list.size();
                renderLabel(list, index);
                break;
            case DOWN:
                index = (index - 1) % list.size();
                index = index < 0 ? list.size() - 1
                                  : index;
                renderLabel(list, index);
                break;
            case MENU_1:
                return;
            case SUBMIT:
                onSubmit.accept(index);
                return;
            }
            key = inputReader.readFromInput();
        }
    }

    private <E extends Renderable> void renderLabel(final List<Class<E>> component,
                                                    final int index) {
        Class<? extends Renderable> renderableClass = component.get(index);
        renderer.renderLabel(context.getBean(renderableClass));
    }
}
