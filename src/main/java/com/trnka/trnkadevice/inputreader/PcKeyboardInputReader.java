package com.trnka.trnkadevice.inputreader;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Component
@Profile("dev")
@Slf4j
public class PcKeyboardInputReader implements InputReader {

    private volatile Keystroke pressedKey = null;

    public PcKeyboardInputReader() {
        registerListener();
    }

    @Override
    public Keystroke readFromInput() {

        while (this.pressedKey == null) {
            // iterate
        }
        Keystroke tmpPressedKey = pressedKey;
        pressedKey = null;

        log.info("Keystroke: " + tmpPressedKey.getValue());
        return tmpPressedKey;
    }

    public void setPressedKey(Keystroke pressedKey) {
        this.pressedKey = pressedKey;
    }

    private class KeyListener implements NativeKeyListener {
        @Override
        public void nativeKeyTyped(NativeKeyEvent nativeEvent) {
        }

        @Override
        public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
            String keyText = NativeKeyEvent.getKeyText(nativeEvent.getKeyCode());
            // System.out.println("User typed: " + keyText);
            Keystroke keystroke = Optional.ofNullable(PcKeystroak.MAP.get(nativeEvent.getKeyCode())).orElse(Keystroke.UNKNOWN);
            setPressedKey(keystroke);
        }

        @Override
        public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
        }
    }

    private void registerListener() {
        try {
            LogManager.getLogManager().reset();
            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.OFF);

            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(new PcKeyboardInputReader.KeyListener() {

            });
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
    }

}
