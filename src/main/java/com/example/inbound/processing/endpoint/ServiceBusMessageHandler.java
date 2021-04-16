package com.example.inbound.processing.endpoint;

import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.example.inbound.processing.domain.BusinessMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

@Service
public class ServiceBusMessageHandler implements MessageHandler {

    private ServiceBusSenderClient serviceBusSenderClient;

    @Autowired
    public ServiceBusMessageHandler(ServiceBusSenderClient serviceBusSenderClient) {
        this.serviceBusSenderClient = serviceBusSenderClient;
    }

    @Override
    public void handleMessage(org.springframework.messaging.Message<?> message) throws MessagingException {

        BusinessMessage businessMessage = (BusinessMessage) message.getPayload();
        ServiceBusMessage serviceBusMessage = new ServiceBusMessage(businessMessage.getBody());
        serviceBusMessage.setSessionId(businessMessage.getSessionId());

        serviceBusSenderClient.sendMessage(serviceBusMessage);
    }
}
