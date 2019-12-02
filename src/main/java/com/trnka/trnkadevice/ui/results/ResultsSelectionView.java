package com.trnka.trnkadevice.ui.results;

import com.trnka.trnkadevice.domain.statistics.SequenceStatistic;
import com.trnka.trnkadevice.repository.ResultsRepository;
import com.trnka.trnkadevice.ui.CycledComponent;
import com.trnka.trnkadevice.ui.IView;
import com.trnka.trnkadevice.ui.UserSession;
import com.trnka.trnkadevice.ui.navigation.Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.inputreader.InputReader;
import com.trnka.trnkadevice.inputreader.Keystroke;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.ui.messages.Messages;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ResultsSelectionView implements IView {

    private IRenderer renderer;
    private InputReader inputReader;
    private UserSession userSession;
    private ResultsRepository resultsRepository;
    private Navigator navigator;
    private ResultView resultView;

    private CycledComponent cycledComponent;

    @Autowired
    public ResultsSelectionView(final IRenderer renderer,
                                final InputReader inputReader,
                                final UserSession userSession,
                                final ResultsRepository resultsRepository,
                                final CycledComponent cycledComponent,
                                final Navigator navigator,
                                final ResultView resultView) {
        this.renderer = renderer;
        this.inputReader = inputReader;
        this.userSession = userSession;
        this.resultsRepository = resultsRepository;
        this.cycledComponent = cycledComponent;
        this.navigator = navigator;
        this.resultView = resultView;
    }

    @Override
    public void enter() {
        renderer.renderMessage(Messages.RESULTS_VIEW);

        List<SequenceStatistic> results = resultsRepository.findAllTestResultsForUser(userSession.getUserId());

        if (results.isEmpty()) {
            renderer.renderMessage(Messages.RESULT_NO_RESULTS_TO_DISPLAY);
            waitForMainMenuClick();
            return;
        }
        List<ResultsComponent> resultComponents = results.stream().map(res -> new ResultsComponent(res)).collect(Collectors.toList());

        cycledComponent.cycleThroughComponents(selectedIndex -> navigateToSelectedResults(resultComponents.get(selectedIndex)), resultComponents);
    }
aaaa
    private void waitForMainMenuClick() {
        Keystroke key = inputReader.readFromInput();
        while (key != null) {
            key = inputReader.readFromInput();
        }
    }

    private void navigateToSelectedResults(final ResultsComponent resultsComponent) {
        resultView.refresh(resultsComponent.getStatistic());
        navigator.navigate(resultView);
    }

    @Override
    public Messages getLabel() {
        return Messages.RESULTS_LABEL;
    }

    @Override
    public List<String> getMessageParams() {
        return Collections.emptyList();
    }

}