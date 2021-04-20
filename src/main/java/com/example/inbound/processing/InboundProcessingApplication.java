package com.example.inbound.processing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication
@EnableIntegration
@ImportResource({
        "classpath:META-INF/spring/amqp-connectivity.xml",
        "classpath:META-INF/spring/amqp-receiver.xml",
        "classpath:META-INF/spring/asb-sender.xml"
})
public class InboundProcessingApplication {

    public static void main(String[] args) {
        SpringApplication.run(InboundProcessingApplication.class, args);
    }

}
