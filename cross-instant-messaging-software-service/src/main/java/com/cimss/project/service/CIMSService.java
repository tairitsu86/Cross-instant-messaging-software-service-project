package com.cimss.project.service;

import com.cimss.project.database.entity.Group;
import com.cimss.project.database.entity.User;

import java.util.List;

public interface CIMSService {

    String broadcast(String groupId,String text);

    String broadcastAll(String text);

    String sendTextMessage(String instantMessagingSoftware,String userid,String textMessage);

    User userRegister(String instantMessagingSoftware, String instantMessagingSoftwareUserId, String userName);

    String join(String instantMessagingSoftware, String instantMessagingSoftwareUserId,String groupId);

    String leave(String instantMessagingSoftware, String instantMessagingSoftwareUserId,String groupId);

    String grantPermission(String instantMessagingSoftware, String instantMessagingSoftwareUserId,String groupId);

    Group newGroup(String groupName,String groupWebhook);

    String renameGroup(String groupId,String groupName);

    String restateGroup(String groupId,String groupDescription);

    String alterGroup(String groupId,String property,String value);

    String alterGroup(Group group);
    List<Group.GroupData> searchGroup(String groupName);

    void TextEventHandler(String instantMessagingSoftware, String instantMessagingSoftwareUserId, String text);

}
