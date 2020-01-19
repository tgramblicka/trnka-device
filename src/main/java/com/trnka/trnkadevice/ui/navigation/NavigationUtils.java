package com.trnka.trnkadevice.ui.navigation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.inputreader.Keystroke;
import com.trnka.trnkadevice.ui.interaction.UserInteraction;
import com.trnka.trnkadevice.ui.interaction.UserInteractionHandler;

@Component
public class NavigationUtils {

    @Autowired
    private ApplicationContext context;

    public void waitForMenuClick() {

        UserInteractionHandler userInteractionHandler = context.getBean(UserInteractionHandler.class);
        UserInteraction userInteraction = userInteractionHandler.readUserInteraction();
        while (!userInteraction.isFlowBreakingCondition() && userInteraction.getKeystroke() != Keystroke.MENU_1) {
            userInteraction = userInteractionHandler.readUserInteraction();
        }
    }
}
