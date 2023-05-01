package com.cimss.project.service.token;

public enum PermissionList {
    NONE,NORMAL,MANAGER,ADMIN;
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
