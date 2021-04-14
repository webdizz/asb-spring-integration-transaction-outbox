package com.example.inbound.processing.tracing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageTracer {

    public void printMetaData(Message<?> message) {
        MessageHeaders headers = message.getHeaders();
        String sessionID = String.valueOf(headers.get("SessionID"));
        String consumerId = String.valueOf(headers.get("ConsumerID"));
        log.info("=== Received a message={} for sessionId={} consumed={}", message.getPayload(), sessionID, consumerId);
    }
}
