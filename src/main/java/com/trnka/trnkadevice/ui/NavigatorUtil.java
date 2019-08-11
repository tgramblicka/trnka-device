package com.trnka.trnkadevice.ui;

import com.trnka.trnkadevice.inputreader.InputReader;
import com.trnka.trnkadevice.inputreader.Keystroke;

public class NavigatorUtil {

    public static void mainMenuNavigation(Navigator navigator){
        Keystroke key = InputReader.readKey();
        while (!key.equals(Keystroke.MENU_1)) {
            key = InputReader.readKey();
        }
        navigator.navigate(MenuStudentView.class);
    }
}
