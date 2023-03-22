package com.my.im.study.service;

import com.my.im.study.database.entity.User;

public interface CrossPlatformService {

    String broadcast(String instantMessagingSoftware,String instantMessagingSoftwareUserId,String groupId,String text);

    String sendTextMessage(String platform,String userid,String textMessage);

    User userRegister(String instantMessagingSoftware, String instantMessagingSoftwareUserId, String userName);

}
