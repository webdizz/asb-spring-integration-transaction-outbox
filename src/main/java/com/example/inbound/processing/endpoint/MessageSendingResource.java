package com.example.inbound.processing.endpoint;

import com.azure.core.util.IterableStream;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.azure.messaging.servicebus.ServiceBusSessionReceiverClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import java.util.UUID;

@RestController
@RequestMapping("/msg")
@Slf4j
public class MessageSendingResource {


    private JmsConnectionFactory jmsConnectionFactory;

    @Qualifier("inboundQueue")
    private Destination destination;

    @Autowired
    private ServiceBusSenderClient serviceBusSenderClient;

    @Autowired
    private ServiceBusSessionReceiverClient serviceBusReceiverClient;

    public MessageSendingResource(JmsConnectionFactory jmsConnectionFactory, Destination destination) {
        this.jmsConnectionFactory = jmsConnectionFactory;
        this.destination = destination;
    }

    @PostMapping("/send")
    public String send(@RequestParam("s") String sessionId) {
        String messageBody = UUID.randomUUID().toString();
        try (Connection connection = jmsConnectionFactory.createConnection()) {
            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(destination);
            Message message = session.createTextMessage(messageBody);
            message.setStringProperty("SessionID", sessionId);
            message.setStringProperty("SessionId", sessionId);
            message.setStringProperty("JMSXGroupID", sessionId);
            message.setStringProperty("GroupId", sessionId);
            producer.send(message);

            ServiceBusMessage serviceBusMessage = new ServiceBusMessage(messageBody);
            serviceBusMessage.setSessionId(sessionId);
            serviceBusSenderClient.sendMessage(serviceBusMessage);

        } catch (JMSException e) {
            log.error("Unable to send message", e);
        }

        return "Message was sent: " + messageBody;
    }

    @GetMapping("/receive")
    public String receive(@RequestParam("s") String sessionId) {
        String messageBody = "None";
        IterableStream<ServiceBusReceivedMessage> receivedMessages = serviceBusReceiverClient.acceptSession(sessionId).receiveMessages(1);
        ServiceBusReceivedMessage receivedMessage = receivedMessages.stream().findFirst().get();
        return receivedMessage.getBody().toString();
    }

}
