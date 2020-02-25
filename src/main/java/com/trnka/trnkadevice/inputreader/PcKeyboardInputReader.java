package com.trnka.trnkadevice.inputreader;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Component
@Profile("dev")
@Slf4j
public class PcKeyboardInputReader implements InputReader {

    private volatile Keystroke pressedKey = null;

    /**
     * Registering and unregistering GlobalScreen hook on every keystroke has a readon.
     * If a hook is registered, then debugging app freezes whole intellij because of repetetive listener events,
     * limiting the hook to be alive only during the reading keystroke method enables the whole app to be possible to debug.
     */
    @Override
    public Keystroke readFromInput() {
        registerListener(); // read comment on method

        while (this.pressedKey == null) {
            // iterates
        }
        Keystroke tmpPressedKey = pressedKey;
        pressedKey = null;

        // log.info("Keystroke: " + tmpPressedKey.getValue());

        unregisterListener(); // read comment on method
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
            Logger logger = Logger.getLogger(CustomGlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.OFF);

            CustomGlobalScreen.registerNativeHook();
            KeyListener registeredKeyListener = new KeyListener() {
            };
            CustomGlobalScreen.addNativeKeyListener(registeredKeyListener);
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
    }

    private void unregisterListener() {
        try {
            // CustomGlobalScreen.removeNativeKeyListener(registeredKeyListener);
            CustomGlobalScreen.removeAllListeners();
            CustomGlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
    }

}
