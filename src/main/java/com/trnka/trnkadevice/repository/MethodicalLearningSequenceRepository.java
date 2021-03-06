package com.trnka.trnkadevice.repository;

import org.springframework.data.repository.CrudRepository;

import com.trnka.trnkadevice.domain.MethodicalLearningSequence;

import java.util.Optional;

public interface MethodicalLearningSequenceRepository extends CrudRepository<MethodicalLearningSequence, Long> {

    Optional<MethodicalLearningSequence> findByLevel(final Integer level);
}
