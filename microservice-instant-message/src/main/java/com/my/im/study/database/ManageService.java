package com.my.im.study.database;

public interface ManageService {

    public void givePermission(String instantMessagingSoftware,String instantMessagingSoftwareUserId,String groupId);

    public boolean checkPermission(String instantMessagingSoftware,String instantMessagingSoftwareUserId,String groupId);
}
