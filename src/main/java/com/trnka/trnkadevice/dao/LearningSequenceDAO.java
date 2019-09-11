package com.trnka.trnkadevice.dao;

import com.trnka.trnkadevice.domain.LearningSequence;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class LearningSequenceDAO {

    public Set<LearningSequence> getLearningSequences(final String username) {
        return MockData.SEQUENCES;
    }
}
