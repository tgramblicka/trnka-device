package com.trnka.trnkadevice.dao;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trnka.trnkadevice.domain.LearningSequence;
import com.trnka.trnkadevice.repository.LearningSequenceRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LearningSequenceDAO {

    @Autowired
    private LearningSequenceRepository repo;

    public Set<LearningSequence> getLearningSequences(final String username) {
        Set<LearningSequence> result = new HashSet<>();
        repo.findAll().forEach(s -> result.add(s));
        return result;
    }
}
