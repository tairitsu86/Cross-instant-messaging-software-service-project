package com.my.im.study.service;

public interface CrossPlatformService {

    String broadcast(String userId,String groupId,String text);

    String sendTextMessage(String platform,String userid,String textMessage);

}
