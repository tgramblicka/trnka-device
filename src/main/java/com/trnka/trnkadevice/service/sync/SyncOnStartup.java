package com.trnka.trnkadevice.service.sync;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SyncOnStartup implements InitializingBean {

    @Autowired
    private SyncService syncService;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Will sync all the data from VST");
        try {
//            syncService.synchronize();
        } catch (FeignException e) {
            log.warn("VST backend is not available, or threw an error");
        } catch (Exception e) {
            log.error("An exception occured during sync with VST.", e);
        }
    }

}
