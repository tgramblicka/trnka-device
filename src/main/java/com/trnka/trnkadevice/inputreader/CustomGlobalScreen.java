package com.trnka.trnkadevice.inputreader;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 * Customized GlobalScreen removing all the listeners with simple method.
 */
public class CustomGlobalScreen extends GlobalScreen {

    public static void removeAllListeners(){
        NativeKeyListener[] listeners = eventListeners.getListeners(NativeKeyListener.class);
        for (NativeKeyListener listener : listeners){
            removeNativeKeyListener(listener);
        }
    }
}
