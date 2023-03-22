package com.my.im.study.database;

import com.my.im.study.database.entity.Manager;

import java.util.List;

public interface ManagerService {

    void grantPermission(String instantMessagingSoftware,String instantMessagingSoftwareUserId,String groupId);

    void revokePermission(String instantMessagingSoftware,String instantMessagingSoftwareUserId,String groupId);

    boolean checkPermission(String instantMessagingSoftware,String instantMessagingSoftwareUserId,String groupId);

    List<Manager> getAllManagers();

    void deleteAllManagers();
}
