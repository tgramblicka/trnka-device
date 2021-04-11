package com.trnka.trnkadevice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.trnka.trnkadevice.domain.SequenceStatistic;

import java.util.List;

public interface SequenceStatisticRepository extends CrudRepository<SequenceStatistic, Long> {

    List<SequenceStatistic> findBySequence_Id(Long id);

    List<SequenceStatistic> findByUser_Id(Long id);

    @Query("SELECT stats from SequenceStatistic  stats INNER JOIN stats.sequence as seq WHERE TYPE(seq) = TestingSequence AND stats.user.id = :userId")
    List<SequenceStatistic> findAllTestResultsForUser(Long userId);


}
