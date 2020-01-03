package com.trnka.trnkadevice.repository;

import org.springframework.data.repository.CrudRepository;

import com.trnka.trnkadevice.domain.Sequence;

public interface SequenceRepository extends CrudRepository<Sequence, Long> {

}
