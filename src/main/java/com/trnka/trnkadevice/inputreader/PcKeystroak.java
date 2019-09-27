package com.trnka.trnkadevice.inputreader;

import java.util.HashMap;
import java.util.Map;

public class PcKeystroak {
    public static final Map<Integer, Keystroke> MAP = new HashMap<>();

    static {
        MAP.put(57416, Keystroke.UP);
        MAP.put(57424, Keystroke.DOWN);

        MAP.put(28, Keystroke.SUBMIT);
        MAP.put(30, Keystroke.KEY_A);
        MAP.put(48, Keystroke.KEY_B);
        MAP.put(8, Keystroke.BRAIL_KEY_1); // KEY 7
        MAP.put(8, Keystroke.BRAIL_KEY_2); // KEY 8
        MAP.put(5, Keystroke.BRAIL_KEY_3); // KEY 4
        MAP.put(6, Keystroke.BRAIL_KEY_4); // KEY 4
        MAP.put(2, Keystroke.BRAIL_KEY_5); // KEY 1
        MAP.put(3, Keystroke.BRAIL_KEY_6); // KEY 2


    }

}
