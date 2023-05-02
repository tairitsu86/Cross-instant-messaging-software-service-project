package com.cimss.project.database.entity;

import com.cimss.project.service.token.InstantMessagingSoftwareList;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserId implements Serializable {
    @Schema(description = "該用戶使用的即時通訊軟體全大寫英文名稱",example = "LINE")
    private String instantMessagingSoftware;
    @Schema(description = "該用戶的即時通訊軟體ID",example = "U11111111111111111111111111111111")
    private String instantMessagingSoftwareUserId;

    public static UserId CreateUserId(String instantMessagingSoftware,String instantMessagingSoftwareUserId){
        return new UserId(instantMessagingSoftware,instantMessagingSoftwareUserId);
    }
    public static UserId CreateUserId(InstantMessagingSoftwareList instantMessagingSoftware, String instantMessagingSoftwareUserId){
        return new UserId(instantMessagingSoftware.name(),instantMessagingSoftwareUserId);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserId userId = (UserId) o;
        return instantMessagingSoftware.equals(userId.instantMessagingSoftware) &&
                instantMessagingSoftwareUserId.equals(userId.instantMessagingSoftwareUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instantMessagingSoftware, instantMessagingSoftwareUserId);
    }
}
