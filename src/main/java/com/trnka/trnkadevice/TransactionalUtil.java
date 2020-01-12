package com.trnka.trnkadevice;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionalUtil {

    @Transactional
    public void runInTransaction(Runnable runnable){
        runnable.run();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void runInNewTransaction(Runnable runnable){
        runnable.run();
    }
}
