package com.cimss.project.database.entity.token;

import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

public enum DeliveryMode {
    WEBHOOK,RABBITMQ;
    @Data
    @AllArgsConstructor
    public static class DeliveryConfig {
        @Embedded
        private WebhookConfig webhookConfig;
        @Embedded
        private RabbitMQConfig rabbitMQConfig;
        @Data
        @AllArgsConstructor
        public static class WebhookConfig{
            private String url;
            @Embedded
            private Map<String,String> header;
        }
        @Data
        @AllArgsConstructor
        public static class RabbitMQConfig{
            private String serverIP;
            private String username;
            private String password;
            @Embedded
            private Map<String,String> metadata;
        }
    }
}
