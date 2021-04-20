package com.example.inbound.processing.configuration;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.azure.messaging.servicebus.ServiceBusSessionReceiverClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceBusConfiguration {

    public static final String ORDERS_QUEUE_NAME = "orders";

    @Bean
    public ServiceBusSenderClient serviceBusSenderClient() {
        return new ServiceBusClientBuilder()
                .connectionString("Endpoint=sb://sfs-asb-standard.servicebus.windows.net/;SharedAccessKeyName=sfs-asb-user;SharedAccessKey=5ZYWkWvlgCB/nthTq/UnrtzLeOUdxEOw4p4m+/Kw+r0=")
                .sender()
                .queueName(ORDERS_QUEUE_NAME)
                .buildClient();
    }

    @Bean
    public ServiceBusSessionReceiverClient serviceBusSessionReceiverClient() {
        return new ServiceBusClientBuilder()
                .connectionString("Endpoint=sb://sfs-asb-standard.servicebus.windows.net/;SharedAccessKeyName=sfs-asb-user;SharedAccessKey=5ZYWkWvlgCB/nthTq/UnrtzLeOUdxEOw4p4m+/Kw+r0=")
                .sessionReceiver()
                .queueName(ORDERS_QUEUE_NAME)
                .buildClient();
    }

}
