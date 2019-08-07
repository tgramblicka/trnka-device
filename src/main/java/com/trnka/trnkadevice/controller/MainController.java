package com.trnka.trnkadevice.controller;

import com.trnka.trnkadevice.database.DbQueries;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "monitoring")
public class MainController {

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

}
