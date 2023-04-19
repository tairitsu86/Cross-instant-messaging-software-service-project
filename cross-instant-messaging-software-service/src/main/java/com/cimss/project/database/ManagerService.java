package com.cimss.project.database;

import com.cimss.project.database.entity.Manager;

import java.util.List;

public interface ManagerService {

    String grantPermission(String instantMessagingSoftware,String instantMessagingSoftwareUserId,String groupId);

    String revokePermission(String instantMessagingSoftware,String instantMessagingSoftwareUserId,String groupId);

    boolean isManager(String instantMessagingSoftware,String instantMessagingSoftwareUserId,String groupId);

    List<Manager> getAllManagers();

    void deleteAllManagers();

}
