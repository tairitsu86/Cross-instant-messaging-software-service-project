package com.cimss.project.database.entity.token;

import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public enum GroupRole implements Serializable {
    NOT_MEMBER(0),
    GROUP_MEMBER(1),
    GROUP_MANAGER(2),
    GROUP_OWNER(3);

    private int permissionNumber;
    public boolean ownerPermission(){
        return permissionNumber>=GROUP_OWNER.permissionNumber;
    }
    public boolean managerPermission(){
        return permissionNumber>=GROUP_MANAGER.permissionNumber;
    }
    public boolean memberPermission(){
        return permissionNumber>=GROUP_MEMBER.permissionNumber;
    }
    public boolean permission(GroupRole requireRole){
        return this.permissionNumber>=requireRole.permissionNumber;
    }

}
