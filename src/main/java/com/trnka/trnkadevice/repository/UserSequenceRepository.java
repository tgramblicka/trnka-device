package com.trnka.trnkadevice.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.trnka.trnkadevice.domain.UserSequence;

public interface UserSequenceRepository extends CrudRepository<UserSequence, Long> {

    @Query(value = "SELECT usrSeq from UserSequence AS usrSeq JOIN usrSeq.sequence AS seq WHERE seq.id IN (:ids)")
    List<UserSequence> findAllBySequenceIds(Set<Long> ids);


}
