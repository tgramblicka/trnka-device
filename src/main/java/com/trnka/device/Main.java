package com.trnka.device;

import com.trnka.device.evdev.EventDevice;

import java.io.IOException;
import java.io.InputStream;

public class Main {

    public static void main(String[] args) {
        //        RestClient client = new RestClient();
        //        String response = client.sendGetRequest("/monitoring/alive");
        //        System.out.println(response);

        initEvdevListener();
    }

    private static void initEvdevListener() {
        try {
            //        String path = "/dev/input/by-path/platform-20804000.i2c-event";
            String path = "/dev/input/event0";
            EventDevice dev = new EventDevice(path);
            dev.addListener(e -> System.out.println(e));
        } catch (IOException e) {
            System.out.println("Exception durring evdev init: " + e.getStackTrace().toString());
        }
    }

}
