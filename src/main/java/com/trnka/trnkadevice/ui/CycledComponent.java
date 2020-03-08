package com.trnka.trnkadevice.ui;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.inputreader.Keystroke;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.ui.interaction.UserInteraction;
import com.trnka.trnkadevice.ui.interaction.UserInteractionHandler;

@Component
public class CycledComponent {

    private IRenderer renderer;
    private UserInteractionHandler userInteractionHandler;

    @Autowired
    private ApplicationContext context;

    @Autowired
    public CycledComponent(final IRenderer renderer,
                           final UserInteractionHandler userInteractionHandler) {
        this.renderer = renderer;
        this.userInteractionHandler = userInteractionHandler;
    }

    public void cycleThroughComponents(final Consumer<Integer> onSubmit,
                                       final List<? extends Renderable> list) {
        UserInteraction userInteraction = userInteractionHandler.readUserInteraction();
        int index = 0;
        while (!userInteraction.isFlowBreakingCondition()) {
            Keystroke key = userInteraction.getKeystroke();
            switch (key) {
            case UP:
                index = (index + 1) % list.size();
                renderer.renderMessage(list.get(index));
                break;
            case DOWN:
                index = (index - 1) % list.size();
                index = index < 0 ? list.size() - 1
                                  : index;
                renderer.renderMessage(list.get(index));
                break;
            case SUBMIT:
                onSubmit.accept(index);
                return;
            }
            userInteraction = userInteractionHandler.readUserInteraction();
        }
    }

    public <E extends Renderable> void cycleThroughMenu(final Consumer<Integer> onSubmit,
                                                        final Class<E>... renderables) {
        List<Class<E>> list = Stream.of(renderables).collect(Collectors.toList());
        UserInteraction userInteraction = userInteractionHandler.readUserInteraction();
        int index = 0;
        while (!userInteraction.isFlowBreakingCondition()) {
            Keystroke key = userInteraction.getKeystroke();
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
            case SUBMIT:
                onSubmit.accept(index);
                return;
            }
            userInteraction = userInteractionHandler.readUserInteraction();
        }
    }

    private <E extends Renderable> void renderLabel(final List<Class<E>> component,
                                                    final int index) {
        Class<? extends Renderable> renderableClass = component.get(index);
        renderer.renderMessage(context.getBean(renderableClass));
    }
}
