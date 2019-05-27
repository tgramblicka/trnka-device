package com.trnka.device;

import com.trnka.device.rest.RestClient;

public class Main {

    public static void main(String[] args) {
        RestClient client = new RestClient();
        String response = client.sendGetRequest("/monitoring/alive");
        System.out.println(response);
    }
}
