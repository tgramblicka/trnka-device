package com.trnka.trnkadevice.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.trnka.trnkadevice.domain.LearningSequence;

public interface LearningSequenceRepository extends CrudRepository<LearningSequence, Long> {

    @Query(value = "SELECT userSeq from User usr JOIN usr.sequences AS userSeq WHERE usr.username = :username AND type(userSeq) IN ('LS')")
    Set<LearningSequence> findAllLearningSequencesForUser(@Param("username") String username);


}
