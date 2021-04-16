package com.example.inbound.processing.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class BusinessMessage implements Serializable {
    private String sessionId;
    private String body;
}
