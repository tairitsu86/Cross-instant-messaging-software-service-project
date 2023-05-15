package com.cimss.project.database.entity;

import com.cimss.project.service.token.InstantMessagingSoftwareList;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

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
    private String instantMessagingSoftware;
    @Schema(description = "IM user id",example = "U11111111111111111111111111111111")
    @Column(name = "instant_messaging_software_user_id")
    private String instantMessagingSoftwareUserId;

    public static UserId CreateUserId(String instantMessagingSoftware,String instantMessagingSoftwareUserId){
        return new UserId(instantMessagingSoftware,instantMessagingSoftwareUserId);
    }
    public static UserId CreateUserId(InstantMessagingSoftwareList instantMessagingSoftware, String instantMessagingSoftwareUserId){
        return new UserId(instantMessagingSoftware.name(),instantMessagingSoftwareUserId);
    }
}
