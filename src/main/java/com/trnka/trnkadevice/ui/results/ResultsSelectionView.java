package com.trnka.trnkadevice.ui.results;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.trnka.trnkadevice.ui.messages.AudioMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.trnka.trnkadevice.domain.statistics.SequenceStatistic;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.repository.ResultsRepository;
import com.trnka.trnkadevice.ui.CycledComponent;
import com.trnka.trnkadevice.ui.IView;
import com.trnka.trnkadevice.ui.UserSession;
import com.trnka.trnkadevice.ui.messages.Messages;
import com.trnka.trnkadevice.ui.navigation.NavigationUtils;
import com.trnka.trnkadevice.ui.navigation.Navigator;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ResultsSelectionView implements IView {

    private IRenderer renderer;
    private NavigationUtils navigationUtils;
    private UserSession userSession;
    private ResultsRepository resultsRepository;
    private Navigator navigator;
    private ResultView resultView;

    private CycledComponent cycledComponent;

    @Autowired
    public ResultsSelectionView(final IRenderer renderer,
                                final NavigationUtils navigationUtils,
                                final UserSession userSession,
                                final ResultsRepository resultsRepository,
                                final CycledComponent cycledComponent,
                                final Navigator navigator,
                                final ResultView resultView) {
        this.renderer = renderer;
        this.navigationUtils = navigationUtils;
        this.userSession = userSession;
        this.resultsRepository = resultsRepository;
        this.cycledComponent = cycledComponent;
        this.navigator = navigator;
        this.resultView = resultView;
    }

    @Override
    @Transactional
    public void enter() {
        renderer.renderMessage(AudioMessage.of(Messages.RESULTS_SELECTION_VIEW));

        List<SequenceStatistic> results = resultsRepository.findAllTestResultsForUser(userSession.getUserId());

        if (results.isEmpty()) {
            renderer.renderMessage(AudioMessage.of(Messages.RESULT_NO_RESULTS_TO_DISPLAY));
            navigationUtils.waitForMenuClick();
            return;
        }
        List<ResultsComponent> resultComponents = results.stream().map(res -> new ResultsComponent(res)).collect(Collectors.toList());
        cycledComponent.cycleThroughComponents(selectedIndex -> navigateToSelectedResults(resultComponents.get(selectedIndex)), resultComponents);
    }



    private void navigateToSelectedResults(final ResultsComponent resultsComponent) {
        resultView.refresh(resultsComponent.getStatistic());
        navigator.navigateAsync(resultView.getClass());
    }

    @Override
    public Messages getMessage() {
        return Messages.RESULTS_LABEL_MENU;
    }

    @Override
public List<Messages> getParams() {
        return Collections.emptyList();
    }

}
