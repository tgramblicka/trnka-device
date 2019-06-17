package com.trnka.device;

import com.trnka.device.inputreader.InputReader;

public class Main {

    public static void main(String[] args) {
        //        RestClient client = new RestClient();
        //        String response = client.sendGetRequest("/monitoring/alive");
        //        System.out.println(response);

        readInput();
    }

    private static void readInput() {
        try {
            InputReader.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
