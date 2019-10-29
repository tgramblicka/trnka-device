package com.trnka.trnkadevice.ui;

import com.trnka.trnkadevice.repository.ResultsRepository;
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
    private UserSession userSession;
    private ResultsRepository resultsRepository;

    @Autowired
    public ResultsView(final IRenderer renderer,
                       final InputReader inputReader,
                       final UserSession userSession,
                       final ResultsRepository resultsRepository) {
        this.renderer = renderer;
        this.inputReader = inputReader;
        this.userSession = userSession;
        this.resultsRepository = resultsRepository;
    }

    @Override
    public void enter() {
        renderer.renderMessage(Messages.RESULTS_VIEW);

        resultsRepository.findAllTestResultsForUser(userSession.getUserId());
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
