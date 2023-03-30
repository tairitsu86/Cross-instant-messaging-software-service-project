package com.my.im.study.apibody;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EventBody {
    private String eventType;
    private String instantMessagingSoftware;
    private String message;
    public EventBody(String eventType,String message){
        this.eventType = eventType;
        this.message = message;
    }
}
