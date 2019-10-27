package com.trnka.trnkadevice.inputreader;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.trnka.trnkadevice.ui.navigation.Navigator;

import lombok.extern.slf4j.Slf4j;

@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Component
@Profile("dev")
@Slf4j
public class PcKeyboardInputReader implements InputReader {

    private volatile Keystroke pressedKey = null;
    private SpecialKeyBehaviourHandler specialKeyBehaviourHandler;

    public PcKeyboardInputReader() {
        registerListener();
        specialKeyBehaviourHandler = new SpecialKeyBehaviourHandler();
    }

    @Autowired
    private Navigator navigator;

    @Override
    public Keystroke readFromInput() {

        while (this.pressedKey == null) {
            // iterate
        }
        Keystroke tmpPressedKey = pressedKey;
        pressedKey = null;

//        log.info("Keystroke: " + tmpPressedKey.getValue());
        Optional<BiConsumer<Keystroke, Navigator>> specialBehaviour = specialKeyBehaviourHandler.getSpecialKeyBehaviour(tmpPressedKey);
        if (specialBehaviour.isPresent()) {
            specialBehaviour.get().accept(tmpPressedKey, navigator);
            return tmpPressedKey;
        }
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
