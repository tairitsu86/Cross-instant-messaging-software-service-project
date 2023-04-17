package com.cimss.project.database;

import com.cimss.project.database.entity.Manager;

import java.util.List;

public interface ManagerService {

    void grantPermission(String instantMessagingSoftware,String instantMessagingSoftwareUserId,String groupId);

    void revokePermission(String instantMessagingSoftware,String instantMessagingSoftwareUserId,String groupId);

    boolean isManager(String instantMessagingSoftware,String instantMessagingSoftwareUserId,String groupId);

    List<Manager> getAllManagers();

    void deleteAllManagers();

}
