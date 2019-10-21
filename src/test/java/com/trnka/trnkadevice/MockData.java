package com.trnka.trnkadevice;

import java.util.HashSet;
import java.util.Set;

import com.trnka.trnkadevice.domain.BrailCharacter;
import com.trnka.trnkadevice.domain.LearningSequence;
import com.trnka.trnkadevice.domain.Step;
import com.trnka.trnkadevice.repository.BrailCharacterRepository;
import com.trnka.trnkadevice.repository.LearningSequenceRepository;
import com.trnka.trnkadevice.ui.messages.Messages;

public class MockData {
    private static final Long TIMEOUT = 100000L;

    private BrailCharacterRepository brailRepository;
    private LearningSequenceRepository learningSequenceRepository;

    public MockData(BrailCharacterRepository brailRepository,
                    LearningSequenceRepository learningSequenceRepository) {
        this.brailRepository = brailRepository;
        this.learningSequenceRepository = learningSequenceRepository;
    }

    public Set<LearningSequence> generateSequences() {
        HashSet<LearningSequence> sequences = new HashSet<>();
        sequences.add(sequence1());
        sequences.add(sequence2());
        return sequences;
    }

    private LearningSequence sequence1() {
        LearningSequence seq = new LearningSequence();
        seq.setAudioMessage(Messages.ONE);
        seq.setAllowedRetries(1);
        seq.setTimeout(TIMEOUT);
        seq.getSteps().add(step("a"));
        seq.getSteps().add(step("b"));
        seq.getSteps().add(step("l"));
        seq.getSteps().add(step("e"));
        return learningSequenceRepository.save(seq);
    }

    private LearningSequence sequence2() {
        LearningSequence seq = new LearningSequence();
        seq.setAudioMessage(Messages.TWO);

        seq.setAllowedRetries(1);
        seq.setTimeout(TIMEOUT);
        seq.getSteps().add(step("k"));
        seq.getSteps().add(step("u"));
        return learningSequenceRepository.save(seq);
    }

    private Step step(String letter) {
        BrailCharacter brailCharacter = brailRepository.findByLetter(letter);

        Step step = new Step();
        step.setPreserveOrder(false);
        step.setBrailCharacter(brailCharacter);
        return step;
    }

}
