package com.cimss.project.service;

import com.cimss.project.database.entity.*;
import com.cimss.project.database.entity.token.GroupRole;
import com.cimss.project.im.ButtonList;

import java.util.List;

public interface CIMSService {
    void broadcast(String groupId,String text,List<UserId> ignoreList);
    void broadcastAll(String text);
    void sendTextMessage(UserId userId, String textMessage);
    void sendButtonListMessage(UserId userId, ButtonList buttonList);

}
