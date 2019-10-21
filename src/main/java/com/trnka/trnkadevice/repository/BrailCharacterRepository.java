package com.trnka.trnkadevice.repository;

import org.springframework.data.repository.CrudRepository;

import com.trnka.trnkadevice.domain.BrailCharacter;

public interface BrailCharacterRepository extends CrudRepository<BrailCharacter, Long> {

    BrailCharacter findByLetter(String letter);
}
