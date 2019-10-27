package com.trnka.trnkadevice.repository;

import org.springframework.data.repository.CrudRepository;

import com.trnka.trnkadevice.domain.statistics.SequenceStatistic;

public interface SequenceStatisticRepository extends CrudRepository<SequenceStatistic, Long> {
}
