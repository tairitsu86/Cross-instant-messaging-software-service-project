package com.cimss.project.apibody;

import com.cimss.project.database.entity.User;
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
    private User user;
    private String message;
//    private Object eventObject;

    public static EventBean createTestEventBean(){
        return new EventBean("Test",null,null);
    }
    public static EventBean createTextMessageEventBean(User user,String message){
        return new EventBean("TextMessage",user,message);
    }
    public static EventBean createJoinEventBean(User user,String message){
        return new EventBean("Join",user,message);
    }
    public static EventBean createLeaveEventBean(User user,String message){
        return new EventBean("Leave",user,message);
    }
//Transfer instant messaging software event
//    public static EventBean createTransferEventBean(String instantMessagingSoftware,Object eventObject){
//        return new EventBean("Transfer",instantMessagingSoftware,null,null,eventObject);
//    }
}
