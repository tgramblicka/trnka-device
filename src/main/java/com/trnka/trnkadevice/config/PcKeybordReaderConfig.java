package com.trnka.trnkadevice.config;

import com.trnka.trnkadevice.inputreader.PcKeyboardInputReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.trnka.trnkadevice.inputreader.InputReader;

@Configuration
@Profile("dev")
public class PcKeybordReaderConfig {
    @Bean
    public InputReader inputReader() {
        return new PcKeyboardInputReader();
    }

}
