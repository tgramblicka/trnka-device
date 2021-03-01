package com.trnka.trnkadevice.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.trnka.trnkadevice.domain.Sequence;

public interface SequenceRepository extends CrudRepository<Sequence, Long> {

    Optional<Sequence> findByExternalId(final Long id);

    List<Sequence> findByExternalIdIn(final Set<Long> id);

    @Query(value = "SELECT step.externalId from Sequence seq JOIN seq.steps AS step WHERE seq.id =:sequenceId")
    Set<Long> getSequenceStepExternalIds(final Long sequenceId);


    @Query(value = "SELECT seq from Sequence seq WHERE seq.externalId NOT IN (:ids) AND type(userSeq) IN ('LS')")
    Set<Sequence> findLearningAndTestingSequencesByExternalIdNotIn(Set<Long> ids);




}


