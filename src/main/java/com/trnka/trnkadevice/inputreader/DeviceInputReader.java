package com.trnka.trnkadevice.inputreader;

import com.trnka.trnkadevice.ui.navigation.Navigator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.function.BiConsumer;

@Slf4j
@Component
@Profile("production")
public class DeviceInputReader implements InputReader {
    @Autowired
    private Navigator navigator;

    private SpecialKeyBehaviourHandler specialKeyBehaviourHandler;

    public DeviceInputReader() {
        specialKeyBehaviourHandler = new SpecialKeyBehaviourHandler();
    }

    @Override
    public Keystroke readFromInput() {
        return readKey();
    }

    public Keystroke readKey() {
        try {
            DataInputStream tmp = getInputStream();
            extractKey(tmp);

            DataInputStream in = getInputStream();
            Keystroke pressedKey = extractKey(in);

            Optional<BiConsumer<Keystroke, Navigator>> specialBehaviour = specialKeyBehaviourHandler.getSpecialKeyBehaviour(pressedKey);
            if (specialBehaviour.isPresent()) {
                specialBehaviour.get().accept(pressedKey, navigator);
                return pressedKey;
            }
            return pressedKey;
        } catch (IOException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public static void read() throws Exception {
        DataInputStream in = getInputStream();
        byte[] eventBytes = new byte[24];
        while (true) {
            in.readFully(eventBytes);
            int type = getBytesAsWord(eventBytes, 16, 2);
            int code = getBytesAsWord(eventBytes, 18, 2);
            int value = getBytesAsWord(eventBytes, 20, 4);
            // "type" and "code" are in the end just numbers. Each number has a particular key, mapping here:
            // https://github.com/spotify/linux/blob/master/include/linux/input.h
            System.out.printf("LINE1: %s %s %s \n", String.valueOf(type), String.valueOf(code), String.valueOf(value));
        }
    }

    private static Keystroke extractKey(final DataInputStream in) throws IOException {
        byte[] tmpBytes = new byte[24];
        in.readFully(tmpBytes);

        byte[] eventBytes = new byte[24];
        in.readFully(eventBytes);

        int code = getBytesAsWord(eventBytes, 18, 2);
        return Keystroke.getByCode(code);
    }

    private static DataInputStream getInputStream() throws FileNotFoundException {
        BufferedInputStream inBuffered;
        try {
            inBuffered = new BufferedInputStream(new FileInputStream("/dev/input/by-path/platform-3f804000.i2c-event"));
        } catch (FileNotFoundException | ArrayIndexOutOfBoundsException e) {
            inBuffered = new BufferedInputStream(new FileInputStream("/dev/input/by-path/platform-20804000.i2c-event"));
        }

        return new DataInputStream(inBuffered);
    }

    private static int getBytesAsWord(byte[] bytes,
                                      int start,
                                      int numberOfBytes) {
        return ((bytes[start] & 0xff) << 8) | (bytes[numberOfBytes] & 0xff);
    }
}
