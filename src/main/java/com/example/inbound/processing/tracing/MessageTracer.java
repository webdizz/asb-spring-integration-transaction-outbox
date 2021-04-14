package com.example.inbound.processing.tracing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageTracer {

    public void printMetaData(Object message) {
        log.info("Received a message: {}", message);
    }
}
