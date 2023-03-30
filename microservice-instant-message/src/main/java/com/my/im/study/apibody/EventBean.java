package com.my.im.study.apibody;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EventBean {
    private String eventType;
    private String instantMessagingSoftware;
    private String message;
    private Object eventObject;
    public EventBean(String eventType, String message){
        this(eventType,null,message,null);
    }
    public EventBean(String instantMessagingSoftware,Object eventObject){
        this("Transfer",instantMessagingSoftware,"Transfer instant messaging software event",eventObject);
    }
}
