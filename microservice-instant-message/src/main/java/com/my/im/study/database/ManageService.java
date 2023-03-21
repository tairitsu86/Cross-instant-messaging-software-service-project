package com.my.im.study.database;

public interface ManageService {

    public void givePermission(String userId,String groupId);

    public boolean checkPermission(String userId,String groupId);
}
