package com.trnka.trnkadevice;

import com.trnka.trnkadevice.database.DbQueries;
import com.trnka.trnkadevice.inputreader.DeviceInputReader;
import com.trnka.trnkadevice.inputreader.PcKeyboardInputReader;

public class Main {

    public static void main(String[] args) {
        //        RestClientBackend client = new RestClientBackend();
        //        String response = client.sendGetRequest("/monitoring/alive");
        //        System.out.println(response);

        //        readInput();
        PcKeyboardInputReader reader = new PcKeyboardInputReader();
        reader.readFromInput();

//        dbSelectionTest();

//            System.out.println(Math.abs((0 - 1) % 3));
    }

    private static void dbSelectionTest() {
        DbQueries dbQueries = new DbQueries();
        dbQueries.selectionTest();
    }

    private static void readInput() {
        try {
            DeviceInputReader.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
