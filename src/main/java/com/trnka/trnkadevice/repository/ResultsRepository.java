package com.trnka.trnkadevice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.trnka.trnkadevice.domain.statistics.SequenceStatistic;

public interface ResultsRepository extends CrudRepository<SequenceStatistic, Long> {

    @Query("SELECT stats from User user INNER JOIN user.statistics stats INNER JOIN stats.sequence as seq WHERE TYPE(seq) = TestingSequence AND user.id = :userId")
    List<SequenceStatistic> findAllTestResultsForUser(Long userId);
}
