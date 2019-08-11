package com.trnka.trnkadevice.inputreader;

import java.util.EnumSet;

public enum Keystroke {
    BRAIL_KEY_1(241, "1"),
    BRAIL_KEY_2(242, "2"),
    BRAIL_KEY_3(243, "3"),
    BRAIL_KEY_4(244, "4"),
    BRAIL_KEY_5(245, "5"),
    BRAIL_KEY_6(246, "6"),

    UP(67, "up"), // add volume on device
    DOWN(66, "down"), // add volume on device
    SUBMIT(59, "submit"), // help on device

    RESERVED_1(60, "reserve1"),
    RESERVED_2(61, "reserve1"),

    MENU_1(62, "menu1"),
    MENU_2(63, "menu2"),
    MENU_3(64, "menu3"),
    MENU_4(65, "menu4"),

    KEY_1(2, "1"),
    KEY_2(3, "2"),
    KEY_3(4, "3"),
    KEY_4(5, "4"),
    KEY_5(6, "5"),
    KEY_6(7, "6"),
    KEY_7(8, "7"),
    KEY_8(9, "8"),
    KEY_9(10, "9"),
    KEY_0(11, "0"),

    KEY_A(30, "a"),
    KEY_B(45, "b"),

    UNKNOWN(0, "-");

    private final Integer code;
    private final String value;

    Keystroke(Integer code,
              String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static Keystroke getByCode(final Integer code) {
        return EnumSet.allOf(Keystroke.class).stream().filter(key -> key.getCode().equals(code)).findAny().orElse(UNKNOWN);
    }

}
