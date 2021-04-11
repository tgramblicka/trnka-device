package com.trnka.trnkadevice.ui.results;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.trnka.trnkadevice.domain.SequenceStatistic;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.repository.SequenceStatisticRepository;
import com.trnka.trnkadevice.ui.CycledComponent;
import com.trnka.trnkadevice.ui.IView;
import com.trnka.trnkadevice.ui.MenuStudentView;
import com.trnka.trnkadevice.ui.UserSession;
import com.trnka.trnkadevice.ui.messages.AudioMessage;
import com.trnka.trnkadevice.ui.messages.Messages;
import com.trnka.trnkadevice.ui.navigation.NavigationUtils;
import com.trnka.trnkadevice.ui.navigation.Navigator;

import lombok.RequiredArgsConstructor;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequiredArgsConstructor
public class ResultsSelectionView implements IView {

    private final IRenderer renderer;
    private final NavigationUtils navigationUtils;
    private final UserSession userSession;
    private final SequenceStatisticRepository sequenceStatsRepository;
    private final Navigator navigator;
    private final ResultView resultView;
    private final CycledComponent cycledComponent;

    @Override
    @Transactional
    public void enter() {
        renderer.renderMessage(AudioMessage.of(Messages.RESULTS_SELECTION_VIEW));

        List<SequenceStatistic> results = sequenceStatsRepository.findAllTestResultsForUser(userSession.getUserId());

        if (results.isEmpty()) {
            renderer.renderMessage(AudioMessage.of(Messages.RESULT_NO_RESULTS_TO_DISPLAY));
            navigationUtils.waitForFlowBreakingButtonClick();
            return;
        }
        List<ResultsComponent> resultComponents = results.stream().map(res -> new ResultsComponent(res)).collect(Collectors.toList());
        cycledComponent.cycleThroughComponents(selectedIndex -> navigateToSelectedResults(resultComponents.get(selectedIndex)), resultComponents);
    }

    @Override
    public Class<? extends IView> onEscape() {
        return MenuStudentView.class;
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
