package com.trnka.trnkadevice.controller;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

public class RestClient {

    private static final String REST_URI = "http://localhost:8080/trnka-backend/rest/";

    private Client client = ClientBuilder.newClient();

    public String sendGetRequest(final String path) {
        return client.target(REST_URI).path(path).request(MediaType.APPLICATION_JSON).get(String.class);
    }

}
