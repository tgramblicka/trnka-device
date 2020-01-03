package com.trnka.trnkadevice.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.trnka.trnkadevice.domain.statistics.SequenceStatistic;
import com.trnka.trnkadevice.repository.SequenceStatisticRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StatisticDao {

    private SequenceStatisticRepository sequenceStatisticRepository;

    @Autowired
    private StatisticDao(final SequenceStatisticRepository sequenceStatisticRepository) {
        this.sequenceStatisticRepository = sequenceStatisticRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveSequenceStats(SequenceStatistic seqStats1) {
        SequenceStatistic saved = sequenceStatisticRepository.save(seqStats1);
    }

}
