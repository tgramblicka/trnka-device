package com.trnka.trnkadevice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.trnka.trnkadevice.domain.SyncType;
import com.trnka.trnkadevice.domain.Synchronization;

public interface SynchronizationRepository extends CrudRepository<Synchronization, Long> {

    @Query(value = "SELECT sync from Synchronization  sync WHERE sync.type = :type ORDER BY sync.executedOn DESC")
    List<Synchronization> findLastSyncRun(SyncType type);
}
