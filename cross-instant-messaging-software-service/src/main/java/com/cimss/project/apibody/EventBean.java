package com.cimss.project.apibody;

import com.cimss.project.database.entity.Member;
import com.cimss.project.database.entity.token.GroupRole;
import com.cimss.project.database.entity.User;
import com.cimss.project.database.entity.token.InstantMessagingSoftware;
import lombok.*;

public abstract class EventBean {

    public abstract String getEventType();

    public static TestEvent createTestEventBean(){
        return new TestEvent();
    }

    public static FunctionListEvent CreateFunctionListEvent(String message){
        return new FunctionListEvent(message);
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
        private final String eventType="FunctionList";
        private String message;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class ReplyEvent extends EventBean{
        private final String eventType="ReplyEvent";
        private String reportMessage;
        private String broadcastMessage;
        private Boolean broadcastIgnoreExecutor;
    }
}
