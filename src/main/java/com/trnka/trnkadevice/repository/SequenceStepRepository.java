package com.trnka.trnkadevice.repository;

import org.springframework.data.repository.CrudRepository;

import com.trnka.trnkadevice.domain.Step;

public interface SequenceStepRepository extends CrudRepository<Step, Long> {
}
