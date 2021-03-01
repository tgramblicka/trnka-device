package com.trnka.trnkadevice.repository;

import org.springframework.data.repository.CrudRepository;

import com.trnka.trnkadevice.domain.BrailCharacter;

import java.util.Optional;

public interface BrailCharacterRepository extends CrudRepository<BrailCharacter, Long> {

    Optional<BrailCharacter> findByLetter(String letter);
}
