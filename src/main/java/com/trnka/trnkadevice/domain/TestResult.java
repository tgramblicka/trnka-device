package com.trnka.trnkadevice.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "test_result")
@Data
@NoArgsConstructor
public class TestResult {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    @Id
    private Long id;

    @Column(name = "studentId")
    private Long studentId;

}
