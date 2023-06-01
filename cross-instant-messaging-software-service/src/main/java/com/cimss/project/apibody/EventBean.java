package com.cimss.project.apibody;

import lombok.*;

import java.util.Map;

public abstract class EventBean {

    public abstract String getEventType();

    public static TestEvent createTestEventBean(){
        return new TestEvent();
    }

    public static FunctionListEvent CreateFunctionListEvent(String replyToken,String message){
        return new FunctionListEvent(replyToken,message);
    }

    public static MQEvent CreateMQEvent(Map<String,String> metadata,EventBean eventBean){
        return new MQEvent(metadata,eventBean);
    }
    @NoArgsConstructor
    @Data
    public static class TestEvent extends EventBean{
        private final String eventType="Test";
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class FunctionListEvent extends EventBean{
        private final String eventType="FunctionListEvent";
        private String replyToken;
        private String message;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class ReplyEvent extends EventBean{
        private final String eventType="ReplyEvent";
        private String replyToken;
        private String reportMessage;
        private String broadcastMessage;
        private Boolean broadcastIgnoreExecutor;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class MQEvent extends EventBean{
        private final String eventType="MQEvent";
        private Map<String,String> metadata;
        private EventBean event;
    }
}
