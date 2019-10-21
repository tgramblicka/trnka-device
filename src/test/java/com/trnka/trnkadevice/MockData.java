package com.trnka.trnkadevice;

import java.util.HashSet;
import java.util.Set;

import com.trnka.trnkadevice.domain.BrailCharacter;
import com.trnka.trnkadevice.domain.LearningSequence;
import com.trnka.trnkadevice.domain.Step;
import com.trnka.trnkadevice.repository.BrailCharacterRepository;
import com.trnka.trnkadevice.ui.messages.Messages;

public class MockData {
    private static final Long TIMEOUT = 100000L;

    private BrailCharacterRepository brailRepository;

    public MockData(BrailCharacterRepository brailRepository) {
        this.brailRepository = brailRepository;
    }

    public Set<LearningSequence> generateSequences() {
        HashSet<LearningSequence> sequences = new HashSet<>();
        sequences.add(sequence1());
        sequences.add(sequence2());
        return sequences;
    }

    private LearningSequence sequence1() {
        LearningSequence seq = new LearningSequence();
        seq.setId(1L);
        seq.setAudioMessage(Messages.ONE);
        seq.setAllowedRetries(1);
        seq.setTimeout(TIMEOUT);
        seq.getSteps().add(step("a"));
        seq.getSteps().add(step("b"));
        seq.getSteps().add(step("l"));
        seq.getSteps().add(step("e"));
        return seq;
    }

    private LearningSequence sequence2() {
        LearningSequence seq = new LearningSequence();
        seq.setId(2L);
        seq.setAudioMessage(Messages.TWO);

        seq.setAllowedRetries(1);
        seq.setTimeout(TIMEOUT);
        seq.getSteps().add(step("k"));
        seq.getSteps().add(step("u"));
        return seq;
    }

    private Step step(String letter) {
        BrailCharacter brailCharacter = brailRepository.findByLetter(letter);

        Step step = new Step();
        step.setPreserveOrder(false);
        step.setBrailCharacter(brailCharacter);
        return step;
    }

}
