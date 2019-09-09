package com.trnka.trnkadevice.inputreader;

import java.util.HashMap;
import java.util.Map;

public class PcKeystroak {
    public static final Map<Integer, Keystroke> MAP = new HashMap<>();

    static {
        MAP.put(10, Keystroke.KEY_0);
    }

}
