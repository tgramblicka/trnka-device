package com.trnka.trnkadevice.dao;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.trnka.trnkadevice.domain.BrailCharacter;
import com.trnka.trnkadevice.domain.LearningSequence;
import com.trnka.trnkadevice.domain.SequenceStep;
import com.trnka.trnkadevice.inputreader.Keystroke;
import com.trnka.trnkadevice.ui.messages.Messages;

public class MockData {
    private static final Long TIMEOUT = 100000L;
    public static final Set<LearningSequence> SEQUENCES = new HashSet<>();

    static {
        SEQUENCES.add(sequence1());
        SEQUENCES.add(sequence2());
    }

    private MockData() {
        super();
    }

    private static LearningSequence sequence1() {
        LearningSequence seq = new LearningSequence();
        seq.setId(1L);
        seq.setAudioMessage(Messages.ONE);
        seq.setAllowedRetries(1);
        seq.setTimeout(TIMEOUT);
        seq.getSteps().add(letter('a', Keystroke.BRAIL_KEY_1));
        seq.getSteps().add(letter('b', Keystroke.BRAIL_KEY_1, Keystroke.BRAIL_KEY_2));
        seq.getSteps().add(letter('l', Keystroke.BRAIL_KEY_1, Keystroke.BRAIL_KEY_2, Keystroke.BRAIL_KEY_3));
        seq.getSteps().add(letter('e', Keystroke.BRAIL_KEY_1, Keystroke.BRAIL_KEY_5));
        return seq;
    }

    private static LearningSequence sequence2() {
        LearningSequence seq = new LearningSequence();
        seq.setId(2L);
        seq.setAudioMessage(Messages.TWO);

        seq.setAllowedRetries(1);
        seq.setTimeout(TIMEOUT);
        seq.getSteps().add(letter('k', Keystroke.BRAIL_KEY_1, Keystroke.BRAIL_KEY_3));
        seq.getSteps().add(letter('u', Keystroke.BRAIL_KEY_1, Keystroke.BRAIL_KEY_3, Keystroke.BRAIL_KEY_6));
        return seq;
    }

    private static SequenceStep letter(Character character,
                                       Keystroke... keystrokes) {
        BrailCharacter brailCharacter = new BrailCharacter();
        brailCharacter.setBrailRepresentationKeystrokes(Stream.of(keystrokes).collect(Collectors.toList()));
        brailCharacter.setCharacter(character);

        SequenceStep step = new SequenceStep();
        step.setPreserveOrder(false);
        step.setBrailCharacter(brailCharacter);
        return step;
    }
}
