package com.cimss.project.apibody;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventBean {
    private String eventType;
    private String instantMessagingSoftware;
    private String instantMessagingSoftwareUserId;
    private String message;
    private Object eventObject;

    public static EventBean createTestEventBean(){
        return new EventBean("Test",null,null,null,null);
    }
    public static EventBean createTextMessageEventBean(String instantMessagingSoftware,String instantMessagingSoftwareUserId,String message){
        return new EventBean("TextMessage",instantMessagingSoftware,instantMessagingSoftwareUserId,message,null);
    }
    public static EventBean createTextCommandEventBean(String instantMessagingSoftware,String instantMessagingSoftwareUserId,String message){
        return new EventBean("TextCommand",instantMessagingSoftware,instantMessagingSoftwareUserId,message,null);
    }
//Transfer instant messaging software event
    public static EventBean createTransferEventBean(String instantMessagingSoftware,Object eventObject){
        return new EventBean("Transfer",instantMessagingSoftware,null,null,eventObject);
    }
}
