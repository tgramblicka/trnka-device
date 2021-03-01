package com.trnka.trnkadevice.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.trnka.trnkadevice.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByCode(String code);

    Optional<User> findByExternalId(Long id);

    Set<User> findByExternalIdNotIn(Set<Long> ids);

}
