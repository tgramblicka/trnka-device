package com.trnka.trnkadevice.controller;

import java.util.Optional;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.trnka.restapi.dto.UserDTO;
import com.trnka.trnkadevice.ui.LoginView;

@Component
public class RestClientBackend {

    private static final String REST_URI = "http://localhost:8080/trnka-backend/rest/";

    public String sendGetRequest(final String path) {
        Client client = ClientBuilder.newClient();
        return client.target(REST_URI).path(path).request(MediaType.APPLICATION_JSON).get(String.class);
    }

    public Optional<UserDTO> authenticate(String password) {
        // todo verify how to do authentication, there must be a copy of user table on device -> when no internet
        // syncronization job will sync the users everytime

        if (password.equals(LoginView.TEST_PASSWORD)) {
            UserDTO dto = new UserDTO();
            dto.setUserName("testUser");
            return Optional.of(dto);
        }
        return Optional.empty();
    }

}
