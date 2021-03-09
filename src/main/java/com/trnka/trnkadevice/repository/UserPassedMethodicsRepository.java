package com.trnka.trnkadevice.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.trnka.trnkadevice.domain.UserPassedMethodicalSequence;

public interface UserPassedMethodicsRepository extends CrudRepository<UserPassedMethodicalSequence, Long> {

    @Query(value = "SELECT usrSeq from UserPassedMethodicalSequence AS usrSeq WHERE usrSeq.id.sequenceId IN (:ids)")
    List<UserPassedMethodicalSequence> findAllBySequenceIds(Set<Long> ids);

    @Query(value = "SELECT usrSeq from UserPassedMethodicalSequence AS usrSeq WHERE usrSeq.id.userId IN (:ids)")
    List<UserPassedMethodicalSequence> findAllByUserIds(Set<Long> ids);


    List<UserPassedMethodicalSequence> findAllById_UserId(Long userId);
}

