package com.my.im.study.service;

public interface CrossPlatformService {

    String broadcast(String instantMessagingSoftware,String instantMessagingSoftwareUserId,String groupId,String text);

    String sendTextMessage(String platform,String userid,String textMessage);

}
