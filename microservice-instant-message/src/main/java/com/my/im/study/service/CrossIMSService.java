package com.my.im.study.service;

import com.my.im.study.database.entity.Group;
import com.my.im.study.database.entity.User;

import java.util.List;

public interface CrossIMSService {

    String broadcast(String groupId,String text);

    String broadcastAll(String text);

    String sendTextMessage(String instantMessagingSoftware,String userid,String textMessage);

    User userRegister(String instantMessagingSoftware, String instantMessagingSoftwareUserId, String userName);

    String join(String instantMessagingSoftware, String instantMessagingSoftwareUserId,String groupId);

    String grantPermission(String instantMessagingSoftware, String instantMessagingSoftwareUserId,String groupId);

    Group newGroup(String groupName,String groupWebhook);

    List<Group> searchGroup(String groupName);
}
