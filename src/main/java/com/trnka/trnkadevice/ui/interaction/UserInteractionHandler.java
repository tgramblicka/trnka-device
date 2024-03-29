package com.trnka.trnkadevice.ui.interaction;

import java.util.Optional;
import java.util.function.BiConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trnka.trnkadevice.inputreader.InputReader;
import com.trnka.trnkadevice.inputreader.Keystroke;
import com.trnka.trnkadevice.inputreader.SpecialKeyBehaviourHandler;
import com.trnka.trnkadevice.ui.navigation.Navigator;

@Service
public class UserInteractionHandler {

    private Navigator navigator;
    private InputReader inputReader;

    @Autowired
    public UserInteractionHandler(final InputReader inputReader,
                                  final Navigator navigator) {
        this.inputReader = inputReader;
        this.navigator = navigator;
    }

    public UserInteraction readUserInteraction() {
        Keystroke keystroke = inputReader.readFromInput();
        UserInteraction interaction = new UserInteraction(keystroke);
        Optional<BiConsumer<Keystroke, Navigator>> specialBehaviour = new SpecialKeyBehaviourHandler().getSpecialKeyBehaviour(keystroke);
        if (specialBehaviour.isPresent()) {
            specialBehaviour.get().accept(keystroke, navigator);
        }
        return interaction;
    }

}
