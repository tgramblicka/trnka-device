package com.trnka.trnkadevice.ui;

import com.trnka.trnkadevice.inputreader.DeviceInputReader;
import com.trnka.trnkadevice.inputreader.Keystroke;

public class NavigatorUtil {

    public static void registerMainMenuNavigation(Navigator navigator){
        Keystroke key = DeviceInputReader.readKey();
        while (!key.equals(Keystroke.MENU_1)) {
            key = DeviceInputReader.readKey();
        }
        navigator.navigate(MenuStudentView.class);
    }
}
