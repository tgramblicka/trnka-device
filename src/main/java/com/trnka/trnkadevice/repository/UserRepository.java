package com.trnka.trnkadevice.repository;

import com.trnka.trnkadevice.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByCode(String code);

}
