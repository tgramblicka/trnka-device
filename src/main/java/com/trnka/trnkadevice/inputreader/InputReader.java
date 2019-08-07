package com.trnka.trnkadevice.inputreader;

import java.io.*;

public class InputReader {


    public static void read() throws Exception {
        BufferedInputStream inBuffered;
        try {
            inBuffered = new BufferedInputStream(new FileInputStream("/dev/input/by-path/platform-3f804000.i2c-event"));
        } catch (FileNotFoundException | ArrayIndexOutOfBoundsException e) {
            inBuffered = new BufferedInputStream(new FileInputStream("/dev/input/by-path/platform-20804000.i2c-event"));
        }

        DataInputStream in = new DataInputStream(inBuffered);

        byte[] eventBytes = new byte[24];
        while (true) {
            in.readFully(eventBytes);
            int type = getBytesAsWord(eventBytes, 16,2);
            int code = getBytesAsWord(eventBytes, 18,2);
            int value = getBytesAsWord(eventBytes, 20,4);
            // "type" and "code" are in the end just numbers. Each number has a particular key, mapping here: https://github.com/spotify/linux/blob/master/include/linux/input.h
            System.out.printf("LINE1: %s %s %s \n", String.valueOf(type), String.valueOf(code), String.valueOf(value));
        }

    }

    private static int getBytesAsWord(byte[] bytes, int start, int numberOfBytes) {
        return ((bytes[start] & 0xff) << 8) | (bytes[numberOfBytes] & 0xff);
    }
}


