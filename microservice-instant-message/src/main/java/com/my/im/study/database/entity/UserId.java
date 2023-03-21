package com.my.im.study.database.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class UserId implements Serializable {

    private String instantMessagingSoftware;
    private String instantMessagingSoftwareUserId;


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
