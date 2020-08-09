package com.trnka.trnkadevice.ui;

import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.ui.messages.AudioMessage;
import com.trnka.trnkadevice.ui.messages.Messages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequiredArgsConstructor
public class YesOrNoView implements IView {
    private final IRenderer renderer;
    private final CycledComponent cycledComponent;

    private Consumer<Boolean> onYesNoSelectionCallback;
    private Renderable yesNoQuestion;

    private static final AudioMessage YES_MESSAGE = AudioMessage.of(Messages.YES);
    private static final AudioMessage NO_MESSAGE = AudioMessage.of(Messages.NO);
    private static List<Renderable> YES_AND_NO_COMPONENTS = Stream.of(YES_MESSAGE, NO_MESSAGE).collect(Collectors.toList());

    public void refresh(Consumer<Boolean> onYesNoSelection,
                        Renderable yesNoQuestion) {
        this.onYesNoSelectionCallback = onYesNoSelection;
        this.yesNoQuestion = yesNoQuestion;
    }

    @Override
    public void enter() {
        renderer.renderMessage(yesNoQuestion);
        cycledComponent.cycleThroughComponents(index -> actOnYesOrNoSelection(index), YES_AND_NO_COMPONENTS);
    }

    private void actOnYesOrNoSelection(int selectedIndex) {
        Boolean yesSelected = YES_AND_NO_COMPONENTS.get(selectedIndex).getMessage().equals(YES_MESSAGE.getMessage());
        onYesNoSelectionCallback.accept(yesSelected);
    }

    @Override
    public Class<? extends IView> onEscape() {
        return MenuStudentView.class;
    }

    @Override
    public Messages getMessage() {
        return null;
    }

    @Override
    public List<Messages> getParams() {
        return null;
    }
}
