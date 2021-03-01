package com.trnka.trnkadevice.repository;

import org.springframework.data.repository.CrudRepository;

import com.trnka.trnkadevice.domain.SequenceStatistic;

import java.util.List;

public interface SequenceStatisticRepository extends CrudRepository<SequenceStatistic, Long> {

    List<SequenceStatistic> findBySequence_Id(Long id);
}
