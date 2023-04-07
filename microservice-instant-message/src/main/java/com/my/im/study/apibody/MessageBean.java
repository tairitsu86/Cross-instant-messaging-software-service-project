package com.my.im.study.apibody;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageBean {
    private String message;
    public static MessageBean CreateMessageBean(String message){
        return new MessageBean(message);
    }
    public static MessageBean CreateAuthorizationWrongMessageBean(){
        return new MessageBean("Authorization error!");
    }
}
