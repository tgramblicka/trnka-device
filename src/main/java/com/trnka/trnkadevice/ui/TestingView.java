package com.trnka.trnkadevice.ui;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.inputreader.InputReader;
import com.trnka.trnkadevice.inputreader.Keystroke;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.ui.messages.Messages;

@Component
public class TestingView implements IView {

    private IRenderer renderer;
    private InputReader inputReader;

    @Autowired
    public TestingView(final IRenderer renderer,
                       final InputReader inputReader) {
        this.renderer = renderer;
        this.inputReader = inputReader;
    }

    @Override
    public void enter() {
        renderer.renderMessage(Messages.TESTING_VIEW);
        Keystroke key = inputReader.readFromInput();
        while (key != null) {
            key = inputReader.readFromInput();
        }
    }

    @Override
    public Messages getLabel() {
        return Messages.TESTING_LABEL;
    }
}
