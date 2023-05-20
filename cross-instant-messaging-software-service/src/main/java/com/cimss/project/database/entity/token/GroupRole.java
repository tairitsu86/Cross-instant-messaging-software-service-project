package com.cimss.project.database.entity.token;

import java.io.Serializable;

public enum GroupRole implements Serializable {
    NOT_MEMBER,GROUP_MEMBER,GROUP_MANAGER,GROUP_OWNER;
    public boolean ownerPermission(){
        return this==GROUP_OWNER;
    }
    public boolean managerPermission(){
        if(ownerPermission()) return true;
        return this==GROUP_MANAGER;
    }
    public boolean memberPermission(){
        if(managerPermission()) return true;
        return this==GROUP_MEMBER;
    }

}
