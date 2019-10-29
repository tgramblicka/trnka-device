package com.trnka.trnkadevice.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.inputreader.InputReader;
import com.trnka.trnkadevice.inputreader.Keystroke;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.ui.messages.Messages;

@Component
public class ResultsView implements IView {

    private IRenderer renderer;
    private InputReader inputReader;

    @Autowired
    public ResultsView(final IRenderer renderer,
                       final InputReader inputReader) {
        this.renderer = renderer;
        this.inputReader = inputReader;
    }

    @Override
    public void enter() {
        renderer.renderMessage(Messages.RESULTS_VIEW);
        Keystroke key = inputReader.readFromInput();

        while (key != null) {
            key = inputReader.readFromInput();
        }
    }

    @Override
    public Messages getLabel() {
        return Messages.RESULTS_LABEL;
    }
}
