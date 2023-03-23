package com.my.im.study.APIBody;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MessageBody {

    public MessageBody(String message){
        this.message = message;
    }

    @Schema(description = "String message", minLength = 1, example = "Hello!")
    private String message;
    private String instantMessagingSoftwareUserId;
    private String instantMessagingSoftware;
    private String groupId;
}
