package com.trnka.trnkadevice.repository;

import com.trnka.trnkadevice.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByCode(String code);

    @Query(value = "SELECT usr from User usr where usr.code NOT IN (:codes)")
    List<User> findAllWithDifferentCodes(@Param("codes") List<String> codes);


}
