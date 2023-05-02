package com.cimss.project.apibody;

import com.cimss.project.database.entity.Member;
import com.cimss.project.database.entity.User;
import com.cimss.project.service.token.InstantMessagingSoftwareList;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

public class EventBean {

    public static TestEvent createTestEventBean(){
        return new TestEvent();
    }
    public static TextMessageEvent createTextMessageEventBean(User user,Boolean isManager,String message){
        return new TextMessageEvent(Member.CreateMemberData(user,isManager),message);
    }
    public static TransferEvent createTransferEventBean(InstantMessagingSoftwareList instantMessagingSoftware,Object eventObject){
        return new TransferEvent(instantMessagingSoftware,eventObject);
    }
    public String getEventType(){
        return null;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TestEvent extends EventBean{
        private final String eventType="Test";
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TextMessageEvent extends EventBean{
        private final String eventType="TextMessage";
        private Member.MemberData member;
        private String message;
        public void setIsManager(Boolean isManager){
            this.member.setIsManager(isManager);
        }
        public User getUser(){
            return this.member.toUser();
        }
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TransferEvent extends EventBean{
        private final String eventType = "Transfer";
        private InstantMessagingSoftwareList instantMessagingSoftware;
        private Object eventObject;
    }
}
