package com.example.inbound.processing.endpoint;

import com.azure.core.util.IterableStream;
import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.azure.messaging.servicebus.ServiceBusSessionReceiverClient;
import com.example.inbound.processing.domain.BusinessMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;
import java.util.UUID;

@RestController
@RequestMapping("/msg")
@Slf4j
public class MessageSendingResource {


    private JmsConnectionFactory jmsConnectionFactory;

    private Destination destination;

    @Autowired
    private ServiceBusSessionReceiverClient serviceBusReceiverClient;

    @Autowired
    @Qualifier("asbMessageSender")
    private SenderGateway asbMessageSender;

    @Autowired
    @Qualifier("amqpMessageSender")
    private SenderGateway amqpMessageSender;

    public MessageSendingResource(
            @Qualifier("amqpConnectionFactory") JmsConnectionFactory jmsConnectionFactory,
            @Qualifier("amqpInboundQueue") Destination destination) {
        this.jmsConnectionFactory = jmsConnectionFactory;
        this.destination = destination;
    }

    @PostMapping("/asb/send")
    public String sendViaASB(@RequestParam("s") String sessionId) {
        BusinessMessage message = createMessage(sessionId);
        asbMessageSender.send(message);

        return "Message was sent: " + message.getBody();
    }

    @PostMapping("/amq/send")
    public String sendViaAMQP(@RequestParam("s") String sessionId) {
        BusinessMessage message = createMessage(sessionId);
        amqpMessageSender.send(message);

        return "Message was sent: " + message.getBody();
    }

    @GetMapping("/asb/receive")
    public String receive(@RequestParam("s") String sessionId) {
        String messageBody = "None";
        IterableStream<ServiceBusReceivedMessage> receivedMessages = serviceBusReceiverClient.acceptSession(sessionId).receiveMessages(1);
        ServiceBusReceivedMessage receivedMessage = receivedMessages.stream().findFirst().get();
        return receivedMessage.getBody().toString();
    }

    private BusinessMessage createMessage(String sessionId) {
        String messageBody = UUID.randomUUID().toString();
        BusinessMessage message = new BusinessMessage();
        message.setBody(messageBody);
        message.setSessionId(sessionId);
        return message;
    }

}
