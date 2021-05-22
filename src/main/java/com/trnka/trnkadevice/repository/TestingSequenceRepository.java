package com.trnka.trnkadevice.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.trnka.trnkadevice.domain.TestingSequence;

public interface TestingSequenceRepository extends CrudRepository<TestingSequence, Long> {

    @Query(value = "SELECT seq from UserSequence AS usrSeq JOIN usrSeq.sequence AS seq WHERE usrSeq.user.id = :id AND type(seq) IN ('TS')")
    List<TestingSequence> findAllTestingSequencesForUser(@Param("id") Long id);

    Optional<TestingSequence> findByExternalId(final Long id);

    Set<TestingSequence> findByExternalIdNotIn(final Set<Long> id);
}
