package com.example.inbound.processing.endpoint;

import com.example.inbound.processing.domain.BusinessMessage;

public interface SenderGateway {
    void send(BusinessMessage message);
}
