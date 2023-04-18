package com.cimss.project.service.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public enum PermissionList {
    NONE,NORMAL,MANAGER,ADMIN;
    private String permission;
    public boolean adminPermission(){
        return this==ADMIN;
    }
    public boolean managerPermission(){
        if(adminPermission()) return true;
        return this==MANAGER;
    }
    public boolean normalPermission(){
        if(managerPermission()) return true;
        return this==NORMAL;
    }

}
