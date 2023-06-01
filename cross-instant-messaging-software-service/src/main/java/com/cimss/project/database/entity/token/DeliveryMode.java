package com.cimss.project.database.entity.token;

import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

public enum DeliveryMode implements Serializable {
    WEBHOOK,RABBITMQ;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeliveryConfig implements Serializable  {
        @Embedded
        private WebhookConfig webhookConfig;
        @Embedded
        private RabbitMQConfig rabbitMQConfig;
        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class WebhookConfig implements Serializable {
            private String url;
            @Embedded
            private Map<String,String> headers;
        }
        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class RabbitMQConfig implements Serializable {
            private String topic;
            @Embedded
            private Map<String,String> metadata;
        }
    }
}
