package com.cimss.project.database.entity;

import com.cimss.project.database.entity.token.InstantMessagingSoftware;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Embeddable
@EqualsAndHashCode
public class UserId implements Serializable {
    @Schema(description = "Name of IM(all uppercase)",example = "LINE")
    @Column(name = "instant_messaging_software")
    @Enumerated(EnumType.STRING)
    private InstantMessagingSoftware instantMessagingSoftware;
    @Schema(description = "IM user id",example = "U11111111111111111111111111111111")
    @Column(name = "instant_messaging_software_user_id")
    private String instantMessagingSoftwareUserId;
    public static UserId CreateUserId(InstantMessagingSoftware instantMessagingSoftware, String instantMessagingSoftwareUserId){
        return new UserId(instantMessagingSoftware,instantMessagingSoftwareUserId);
    }
    public static UserId MappingFromJson(String json){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json,UserId.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String toJsonString(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
