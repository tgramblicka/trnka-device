package com.trnka.trnkadevice.inputreader;

import java.util.EnumSet;

public enum Keystroke {
    BRAIL_KEY_1(241, "1", false),
    BRAIL_KEY_2(242, "2", false),
    BRAIL_KEY_3(243, "3", false),
    BRAIL_KEY_4(244, "4", false),
    BRAIL_KEY_5(245, "5", false),
    BRAIL_KEY_6(246, "6", false),

    UP(67, "up", false), // add volume on device
    DOWN(66, "down", false), // add volume on device
    SUBMIT(59, "submit", false), // help on device

    RESERVED_1(60, "reserve1", true),
    RESERVED_2(61, "reserve1", true),

    MENU_1(62, "menu1", true),
    MENU_2(63, "menu2", true),
    MENU_3(64, "menu3", true),
    MENU_4(65, "menu4", true),

    // KEY_1(2, "1"),
    // KEY_2(3, "2"),
    // KEY_3(4, "3"),
    // KEY_4(5, "4"),
    // KEY_5(6, "5"),
    // KEY_6(7, "6"),
    // KEY_7(8, "7"),
    // KEY_8(9, "8"),
    // KEY_9(10, "9"),
    // KEY_0(11, "0"),

    KEY_A(30, "a", false),
    KEY_B(45, "b", false),

    UNKNOWN(0, "-", false);

    private final Integer code;
    private final String value;
    private final boolean interruptsFurtherProcessing;

    Keystroke(Integer code,
              String value,
              boolean interruptsFurtherProcessing) {
        this.code = code;
        this.value = value;
        this.interruptsFurtherProcessing = interruptsFurtherProcessing;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public boolean getInterruptsFurtherProcessing() {
        return interruptsFurtherProcessing;
    }

    public static Keystroke getByCode(final Integer code) {
        return EnumSet.allOf(Keystroke.class).stream().filter(key -> key.getCode().equals(code)).findAny().orElse(UNKNOWN);
    }

    public static Keystroke getByValue(final String value) {
        return EnumSet.allOf(Keystroke.class).stream().filter(key -> key.getValue().equals(value)).findAny().orElse(UNKNOWN);
    }

}
