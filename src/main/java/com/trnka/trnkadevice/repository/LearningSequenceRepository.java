package com.trnka.trnkadevice.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.trnka.trnkadevice.domain.LearningSequence;

public interface LearningSequenceRepository extends CrudRepository<LearningSequence, Long> {

    @Query(value = "SELECT seq from UserSequence AS usrSeq JOIN usrSeq.sequence AS seq WHERE usrSeq.user.id = :id AND type(seq) IN ('LS')")
    Set<LearningSequence> findAllLearningSequencesForUser(@Param("id") Long id);

    Optional<LearningSequence> findByExternalId(final Long id);

    Set<LearningSequence> findByExternalIdNotIn(final Set<Long> id);


}
