package com.trnka.trnkadevice.inputreader;

import java.util.EnumSet;

public enum KeysMap {
    KEY_A(44),
    KEY_B(45);

    private Integer code;

    KeysMap(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public static KeysMap getByCode(final Integer code) {
        return EnumSet.allOf(KeysMap.class).stream().filter(key -> key.getCode().equals(code)).findAny().orElse(null);
    }

}
