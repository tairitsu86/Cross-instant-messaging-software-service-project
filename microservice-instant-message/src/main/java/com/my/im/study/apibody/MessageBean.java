package com.my.im.study.apibody;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageBean {
    @Schema(description = "執行結果",example = "Success!")
    private String message;
    public static MessageBean CreateMessageBean(String message){
        return new MessageBean(message);
    }
    public static MessageBean CreateAuthorizationWrongMessageBean(){
        return new MessageBean("Authorization error!");
    }
}
