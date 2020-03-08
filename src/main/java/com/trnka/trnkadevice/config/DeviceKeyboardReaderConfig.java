package com.trnka.trnkadevice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.trnka.trnkadevice.inputreader.DeviceInputReader;
import com.trnka.trnkadevice.inputreader.InputReader;

@Configuration
@Profile("production")
public class DeviceKeyboardReaderConfig {
    @Bean
    public InputReader inputReader() {
        return new DeviceInputReader();
    }
}
