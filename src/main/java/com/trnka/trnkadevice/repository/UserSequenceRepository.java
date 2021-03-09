package com.trnka.trnkadevice.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.trnka.trnkadevice.domain.UserSequence;

public interface UserSequenceRepository extends CrudRepository<UserSequence, Long> {

    @Query(value = "SELECT usrSeq from UserSequence AS usrSeq WHERE usrSeq.id.sequenceId IN (:ids)")
    List<UserSequence> findAllBySequenceIds(Set<Long> ids);

    @Query(value = "SELECT usrSeq from UserSequence AS usrSeq WHERE usrSeq.id.userId IN (:ids)")
    List<UserSequence> findAllByUserIds(Set<Long> ids);

    List<UserSequence> findAllById_UserId(Long userId);
}
