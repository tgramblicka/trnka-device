package com.trnka.trnkadevice.repository;

import org.springframework.data.repository.CrudRepository;

import com.trnka.trnkadevice.domain.statistics.SequenceStatistic;

import java.util.List;

public interface ResultsRepository extends CrudRepository<SequenceStatistic, Long> {

    List<SequenceStatistic> findAllTestResultsForUser(Long userId);
}
