package com.trnka.trnkadevice.inputreader;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

import com.trnka.trnkadevice.ui.MenuStudentView;
import com.trnka.trnkadevice.ui.navigation.Navigator;

public class SpecialKeyBehaviourHandler {

    private static Map<Keystroke, BiConsumer<Keystroke, Navigator>> SPECIAL_BEHAVIOURS = new HashMap<>();
    static {
        SPECIAL_BEHAVIOURS.put(Keystroke.MENU_1, (k,
                                                  n) -> n.navigateAsync(MenuStudentView.class));

    }

    public Optional<BiConsumer<Keystroke, Navigator>> getSpecialKeyBehaviour(final Keystroke keystroke) {
        return SPECIAL_BEHAVIOURS.entrySet().stream().filter(entry -> entry.getKey().equals(keystroke)).map(Map.Entry::getValue).findFirst();
    }
}
