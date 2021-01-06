package com.trnka.trnkadevice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.trnka.trnkadevice.domain.SequenceStatistic;
import com.trnka.trnkadevice.repository.SequenceStatisticRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StatisticService {

    private SequenceStatisticRepository sequenceStatisticRepository;

    @Autowired
    public StatisticService(final SequenceStatisticRepository sequenceStatisticRepository) {
        this.sequenceStatisticRepository = sequenceStatisticRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveSequenceStats(SequenceStatistic seqStats1) {
        SequenceStatistic saved = sequenceStatisticRepository.save(seqStats1);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveMethodicalLearingTestStats(SequenceStatistic seqStats1) {
        SequenceStatistic saved = sequenceStatisticRepository.save(seqStats1);


    }



}
