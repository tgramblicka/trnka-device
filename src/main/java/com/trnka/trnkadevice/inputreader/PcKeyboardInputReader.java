package com.trnka.trnkadevice.inputreader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class PcKeyboardInputReader implements InputReader {

    @Override
    public Keystroke readFromInput() {
       read3();
       return null;
    }


    public static void read3() {


        try {
            // Clear previous logging configurations.kjoeri82
            LogManager.getLogManager().reset();


            // Get the logger for "org.jnativehook" and set the level to off.
            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.OFF);

            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(new NativeKeyListener() {

                @Override
                public void nativeKeyTyped(NativeKeyEvent nativeEvent) {
                }

                @Override
                public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
                    String keyText = NativeKeyEvent.getKeyText(nativeEvent.getKeyCode());
                    System.out.println("User typed: " + keyText);
                }

                @Override
                public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
                }
            });
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
    }

}
