package com.trnka.trnkadevice.ui.navigation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.ui.interaction.UserInteraction;
import com.trnka.trnkadevice.ui.interaction.UserInteractionHandler;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class NavigationUtils {

    @Autowired
    private ApplicationContext context;

    public void waitForFlowBreakingButtonClick() {

        UserInteractionHandler userInteractionHandler = context.getBean(UserInteractionHandler.class);
        UserInteraction userInteraction = userInteractionHandler.readUserInteraction();
        while (true){
            userInteraction = userInteractionHandler.readUserInteraction();
        }
    }
}
