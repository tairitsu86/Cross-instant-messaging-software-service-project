package com.cimss.project.database.entity.token;

import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

public enum DeliveryMode implements Serializable {
    WEBHOOK,RABBITMQ;
    @Data
    @AllArgsConstructor
    public static class DeliveryConfig implements Serializable  {
        @Embedded
        private WebhookConfig webhookConfig;
        @Embedded
        private RabbitMQConfig rabbitMQConfig;
        @Data
        @AllArgsConstructor
        public static class WebhookConfig implements Serializable {
            private String url;
            @Embedded
            private Map<String,String> header;
        }
        @Data
        @AllArgsConstructor
        public static class RabbitMQConfig implements Serializable {
            private String serverIP;
            private String username;
            private String password;
            @Embedded
            private Map<String,String> metadata;
        }
    }
}
