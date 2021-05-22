package com.trnka.trnkadevice.ui;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.trnka.trnkadevice.inputreader.Keystroke;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.ui.interaction.UserInteraction;
import com.trnka.trnkadevice.ui.interaction.UserInteractionHandler;
import com.trnka.trnkadevice.ui.navigation.NavigationUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CycledComponent {

    private final IRenderer renderer;
    private final UserInteractionHandler userInteractionHandler;
    private final ApplicationContext context;
    private final NavigationUtils navigationUtils;

    public void cycleThroughComponents(final Consumer<Integer> onSubmit,
                                       final List<? extends Renderable> list) {
        if (CollectionUtils.isEmpty(list)){
            navigationUtils.waitForFlowBreakingButtonClick();
            return;
        }
        renderer.renderMessage(list.get(0));

        UserInteraction userInteraction = userInteractionHandler.readUserInteraction();
        int index = 0;
        while (true) {
            Keystroke key = userInteraction.getKeystroke();
            switch (key) {
            case DOWN:
                index = (index + 1) % list.size();
                renderer.renderMessage(list.get(index));
                break;
            case UP:
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

    public <E extends IView> void cycleThroughMenu(final Consumer<Integer> onSubmit,
                                                   final List<Class<? extends IView>> list) {
        renderFirstElement(list);

        UserInteraction userInteraction = userInteractionHandler.readUserInteraction();
        int index = 0;
        while (true) {
            Keystroke key = userInteraction.getKeystroke();
            switch (key) {
            case DOWN:
                index = (index + 1) % list.size();
                renderLabel(list, index);
                break;
            case UP:
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

    private void renderFirstElement(final List<Class<? extends IView>> list){
        renderLabel(list, 0);
    }

    private void renderLabel(final List<Class<? extends IView>> component,
                             final int index) {
        Class<? extends Renderable> renderableClass = component.get(index);
        renderer.renderMessage(context.getBean(renderableClass));
    }
}
