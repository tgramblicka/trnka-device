package com.trnka.device;

import com.trnka.device.database.DbQueries;
import com.trnka.device.inputreader.InputReader;

public class Main {

    public static void main(String[] args) {
        //        RestClient client = new RestClient();
        //        String response = client.sendGetRequest("/monitoring/alive");
        //        System.out.println(response);

        //        readInput();
        dbSelectionTest();
    }

    private static void dbSelectionTest() {
        DbQueries dbQueries = new DbQueries();
        dbQueries.selectionTest();
    }

    private static void readInput() {
        try {
            InputReader.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
