package com.trnka.trnkadevice.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.trnka.trnkadevice.domain.TestingSequence;

public interface TestingSequenceRepository extends CrudRepository<TestingSequence, Long> {

    @Query(value = "SELECT userSeq from User usr JOIN usr.sequences AS userSeq WHERE usr.username = :username AND type(userSeq) IN ('TS')")
    Set<TestingSequence> findAllTestingSequencesForUser(@Param("username") String username);

    Optional<TestingSequence> findByExternalId(final Long id);

    Set<TestingSequence> findByExternalIdNotIn(final Set<Long> id);
}
