package com.trnka.device.evdev;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Copyright (C) 2009 Giacomo Ferrari
 * This file is part of evdev-java.
 *  evdev-java is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  evdev-java is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with evdev-java.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * Driver for a Linux Evdev character device.
 * <p>
 * Copyright (C) 2009 Giacomo Ferrari
 *
 * @author Giacomo Ferrari
 */

public class EventDevice implements IEventDevice {

    /**
     * Notify these guys about input events.
     */
    private List<InputListener> listeners = new ArrayList<InputListener>();

    /**
     * Device filename we're using.
     */
    String device;

    /**
     * Attached to device we're using.
     */
    private FileChannel deviceInput;
    private ByteBuffer inputBuffer = ByteBuffer.allocate(InputEvent.STRUCT_SIZE_BYTES);

    /**
     * When this is true, the reader thread should terminate ASAP.
     */
    private volatile boolean terminate = false;

    /**
     * This thread repeatedly calls readEvent().
     */
    private Thread readerThread;

    private short[] idResponse = new short[4];

    private int evdevVersionResponse;

    private String deviceNameResponse;

    /**
     * Maps supported event types (keys) to lists of supported event codes.
     */
    private HashMap<Integer, List<Integer>> supportedEvents = new HashMap<Integer, List<Integer>>();

    /**
     * Ensures only one instance of InputAxisParameters is created for each axis (more would be wasteful).
     */
    private HashMap<Integer, InputAxisParameters> axisParams = new HashMap<Integer, InputAxisParameters>();

    /**
     * Create an EventDevice by connecting to the provided device filename.
     * If the device file is accessible, open it and begin listening for events.
     *
     * @param device The path to the device file. Usually one of /dev/input/event*
     * @throws IOException If the device is not found, or is otherwise inaccessible.
     */
    public EventDevice(String device) throws IOException {
        System.loadLibrary("evdev-java");
        this.device = device;
        inputBuffer.order(ByteOrder.LITTLE_ENDIAN);
        initDevice();
    }

    /**
     * Get various ID info. Then, open the file, get the channel, and start the reader thread.
     *
     * @throws IOException
     */
    private void initDevice() throws IOException {

        if (!ioctlGetID(device, idResponse)) {
            System.err.println("WARN: couldn't get device ID: " + device);
            Arrays.fill(idResponse, (short) 0);
        }
        evdevVersionResponse = ioctlGetEvdevVersion(device);
        byte[] devName = new byte[255];
        if (ioctlGetDeviceName(device, devName)) {
            deviceNameResponse = new String(devName);
        } else {
            System.err.println("WARN: couldn't get device name: " + device);
            deviceNameResponse = "Unknown Device";
        }

        readSupportedEvents();

        FileInputStream fis = new FileInputStream(device);
        deviceInput = fis.getChannel();

        readerThread = new Thread() {
            @Override public void run() {
                while (!terminate) {
                    InputEvent ev = readEvent();
                    distributeEvent(ev);
                }
            }
        };
        readerThread.setDaemon(true); /* We don't want this thread to prevent the JVM from terminating */

        readerThread.start();
    }

    /**
     * Get supported events from device, and place into supportedEvents.
     * Adapted from evtest.c.
     */
    private void readSupportedEvents() {
        //System.out.println("Detecting device capabilities...");
        long[][] bit = new long[InputEvent.EV_MAX][NBITS(InputEvent.KEY_MAX)];
        ioctlEVIOCGBIT(device, bit[0], 0, bit[0].length);
        /* Loop over event types */
        for (int i = 0; i < InputEvent.EV_MAX; i++) {
            if (testBit(bit[0], i)) { /* Is this event supported? */
                //System.out.printf("  Event type %d\n", i);
                if (i == 0)
                    continue;
                ArrayList<Integer> supportedTypes = new ArrayList<Integer>();
                ioctlEVIOCGBIT(device, bit[i], i, InputEvent.KEY_MAX);
				/* Loop over event codes for type */
                for (int j = 0; j < InputEvent.KEY_MAX; j++)
                    if (testBit(bit[i], j)) { /* Is this event code supported? */
                        //System.out.printf("    Event code %d\n", j);
                        supportedTypes.add(j);
                    }
                supportedEvents.put(i, supportedTypes);
            }
        }
    }

    private boolean testBit(long[] array, int bit) {
        return ((array[LONG(bit)] >>> OFF(bit)) & 1) != 0;
    }

    private int LONG(int x) {
        return x / (64);
    }

    private int OFF(int x) {
        return x % (64);
    }

    private int NBITS(int x) {
        return ((((x) - 1) / (8 * 8)) + 1);
    }

    /**
     * Distribute an event to all registered listeners.
     *
     * @param ev The event to distribute.
     */
    private void distributeEvent(InputEvent ev) {
        synchronized (listeners) {
            for (InputListener il : listeners) {
                il.event(ev);
            }
        }
    }

    /**
     * Obtain an InputEvent from the input channel. Delegate to InputEvent for parsing.
     *
     * @return
     */
    private InputEvent readEvent() {
        try {
			/* Read exactly the amount of bytes specified by InputEvent.STRUCT_SIZE_BYTES (intrinsic size of inputBuffer)*/
            inputBuffer.clear();
            while (inputBuffer.hasRemaining())
                deviceInput.read(inputBuffer);
			
			/* We want to read now */
            inputBuffer.flip();
			
			/* Delegate parsing to InputEvent.parse() */
            return InputEvent.parse(inputBuffer.asShortBuffer(), device);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     */
    @Override public void close() {
        terminate = true;
        try {
            readerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            deviceInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     */
    @Override public short getBusID() {
        return idResponse[InputEvent.ID_BUS];
    }

    /**
     */
    @Override public String getDeviceName() {
        return deviceNameResponse;
    }

    @Override public short getProductID() {
        // TODO Auto-generated method stub
        return idResponse[InputEvent.ID_PRODUCT];
    }

    /**
     */
    @Override public Map<Integer, List<Integer>> getSupportedEvents() {
        return supportedEvents;
    }

    /**
     */
    @Override public short getVendorID() {
        return idResponse[InputEvent.ID_VENDOR];
    }

    /**
     */
    @Override public int getEvdevVersion() {
        return evdevVersionResponse;
    }

    /**
     */
    @Override public short getVersionID() {
        return idResponse[InputEvent.ID_VERSION];
    }

    @Override public InputAxisParameters getAxisParameters(int axis) {
        InputAxisParameters params;
        if ((params = axisParams.get(axis)) == null) {
            params = new InputAxisParametersImpl(this, axis);
            axisParams.put(axis, params);
        }
        return params;
    }

    /**
     */
    @Override public void addListener(InputListener list) {
        synchronized (listeners) {
            listeners.add(list);
        }
    }

    /**
     */
    @Override public void removeListener(InputListener list) {
        synchronized (listeners) {
            listeners.remove(list);
        }
    }

    public String getDevicePath() {
        return device;
    }

    ////BEGIN JNI METHODS////
    native boolean ioctlGetID(String device, short[] resp);

    native int ioctlGetEvdevVersion(String device);

    native boolean ioctlGetDeviceName(String device, byte[] resp);

    native boolean ioctlEVIOCGBIT(String device, long[] resp, int start, int stop);

    native boolean ioctlEVIOCGABS(String device, int[] resp, int axis);
}

