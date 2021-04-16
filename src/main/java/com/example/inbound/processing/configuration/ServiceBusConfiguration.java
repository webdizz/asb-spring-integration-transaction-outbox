package com.example.inbound.processing.configuration;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.azure.messaging.servicebus.ServiceBusSessionReceiverClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceBusConfiguration {

    @Bean
    public ServiceBusSenderClient serviceBusSenderClient() {
        return new ServiceBusClientBuilder()
                .connectionString("Endpoint=sb://sfs-asb-standard.servicebus.windows.net/;SharedAccessKeyName=sfs-asb-user;SharedAccessKey=5ZYWkWvlgCB/nthTq/UnrtzLeOUdxEOw4p4m+/Kw+r0=")
                .sender()
                .queueName("orders")
                .buildClient();
    }

    @Bean
    public ServiceBusSessionReceiverClient serviceBusReceiverAsyncClient() {
        return new ServiceBusClientBuilder()
                .connectionString("Endpoint=sb://sfs-asb-standard.servicebus.windows.net/;SharedAccessKeyName=sfs-asb-user;SharedAccessKey=5ZYWkWvlgCB/nthTq/UnrtzLeOUdxEOw4p4m+/Kw+r0=")
                .sessionReceiver()
                .queueName("orders")
                .buildClient();
    }

}
