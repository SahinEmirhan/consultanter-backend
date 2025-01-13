package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
public class HandshakeInterceptorConfig {

    @Bean
    public HttpSessionHandshakeInterceptor handshakeInterceptor() {
        return new HttpSessionHandshakeInterceptor();
    }
}
