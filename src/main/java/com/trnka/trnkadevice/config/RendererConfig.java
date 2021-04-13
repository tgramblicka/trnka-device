package com.trnka.trnkadevice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.trnka.trnkadevice.renderer.AudioRenderer;
import com.trnka.trnkadevice.renderer.IRenderer;

@Configuration
public class RendererConfig {

    @Bean
    public IRenderer renderer() {
        return new AudioRenderer();
//         return new ConsolRenderer();
    }
}
