package com.trnka.trnkadevice;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.trnka.trnkadevice.repository.BrailCharacterRepository;
import com.trnka.trnkadevice.repository.LearningSequenceRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@ActiveProfiles("dev")
@Transactional
public class InsertInitialData {

    @Autowired
    private LearningSequenceRepository repo;
    @Autowired
    BrailCharacterRepository brailCharacterRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    @Rollback(false)
    public void fillDB() {
        // MockBrails.BRAIL.forEach(brail -> brailCharacterRepository.save(brail));

        MockData mockData = new MockData(brailCharacterRepository);
        mockData.generateSequences().forEach(sequence -> {
            repo.save(sequence);
        });
    }

}
