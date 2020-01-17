package com.trnka.trnkadevice.ui.navigation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.trnka.trnkadevice.inputreader.InputReader;
import com.trnka.trnkadevice.inputreader.Keystroke;
import org.springframework.stereotype.Component;

@Component
public class NavigationUtils {

    @Autowired
    private ApplicationContext context;

    public void waitForMenuClick() {

        InputReader inputReader = context.getBean(InputReader.class);
        Keystroke key = inputReader.readFromInput();
        while (key != null && key != Keystroke.MENU_1) {
            key = inputReader.readFromInput();
        }
    }
}
