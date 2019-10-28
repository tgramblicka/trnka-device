package com.trnka.trnkadevice.dao;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trnka.trnkadevice.domain.TestingSequence;
import com.trnka.trnkadevice.repository.TestingSequenceRepository;

@Service
public class TestingSequenceDAO {

    @Autowired
    private TestingSequenceRepository repo;

    public Set<TestingSequence> getSequences(final String username) {
        Set<TestingSequence> result = new HashSet<>();
        repo.findAll().forEach(s -> result.add(s));
        return result;
    }
}
