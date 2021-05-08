package com.trnka.trnkadevice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trnka.trnkadevice.AsyncGateway;
import com.trnka.trnkadevice.database.DbQueries;
import com.trnka.trnkadevice.inputreader.DeviceInputReader;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "device")
@Slf4j
public class MainController {

    @Autowired
    private AsyncGateway asyncGateway;

    @GetMapping(path = "health")
    public String health() {
        return "alive";
    }

    @GetMapping
    public String dbSelection() {
        DbQueries dbQueries = new DbQueries();
        dbQueries.selectionTest();
        return "selected";
    }

    @GetMapping(path = "start")
    public String start() {
        try {
            asyncGateway.startAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "started";
    }

    @GetMapping(path = "input")
    public String inputReader() {
        try {
            DeviceInputReader.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "read";
    }



}
