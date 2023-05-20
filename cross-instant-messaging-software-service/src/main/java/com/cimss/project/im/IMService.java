package com.cimss.project.im;

public interface IMService {
    void sendTextMessage(String userId, String textMessage);
    void sendButtonListMessage(String userId, ButtonList buttonList);
}
