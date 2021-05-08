package com.trnka.trnkadevice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.trnka.trnkadevice.domain.SyncType;
import com.trnka.trnkadevice.domain.Synchronization;

public interface SynchronizationRepository extends CrudRepository<Synchronization, Long> {

    @Query(value = "SELECT sync from Synchronization  sync WHERE sync.type = :type and sync.status = com.trnka.trnkadevice.domain.SyncStatus.SUCCESS ORDER BY sync.executedOn DESC")
    List<Synchronization> findLastSuccessfulSyncRun(SyncType type);
}
