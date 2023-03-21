package com.my.im.study.database.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class ManagerId implements Serializable {

    private String instantMessagingSoftwareForeignKey;
    private String instantMessagingSoftwareUserIdForeignKey;
    private String groupIdForeignKey;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ManagerId managerId = (ManagerId) o;
        return instantMessagingSoftwareForeignKey.equals(managerId.instantMessagingSoftwareForeignKey) &&
                instantMessagingSoftwareUserIdForeignKey.equals(managerId.instantMessagingSoftwareUserIdForeignKey)&&
                groupIdForeignKey.equals(managerId.groupIdForeignKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instantMessagingSoftwareForeignKey, instantMessagingSoftwareUserIdForeignKey, groupIdForeignKey);
    }
}
