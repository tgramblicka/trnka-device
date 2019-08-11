package com.trnka.trnkadevice.controller;

import com.trnka.trnkadevice.inputreader.InputReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trnka.trnkadevice.database.DbQueries;
import com.trnka.trnkadevice.ui.LoginView;

@RestController
@RequestMapping(path = "monitoring")
public class MainController {

    @Autowired
    private LoginView loginView;

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
            loginView.enter();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "read";
    }

    @GetMapping(path = "input")
    public String inputReader() {
        try {
            InputReader.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "read";
    }

}
