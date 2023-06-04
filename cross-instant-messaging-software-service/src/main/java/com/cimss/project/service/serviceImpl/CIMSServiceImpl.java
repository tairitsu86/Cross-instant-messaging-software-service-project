package com.cimss.project.service.serviceImpl;


import com.cimss.project.database.DatabaseService;
import com.cimss.project.database.entity.*;
import com.cimss.project.im.ButtonList;
import com.cimss.project.im.IMService;
import com.cimss.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CIMSServiceImpl implements CIMSService {
    @Autowired
    private DatabaseService dataBaseService;
    @Autowired
    @Qualifier("LINE")
    private IMService lineMessageService;
    @Autowired
    @Qualifier("TELEGRAM")
    private IMService telegramMessageService;


    @Override
    public void broadcast(String groupId,String text,List<UserId> ignoreList) {
        List<User> users = dataBaseService.getUsers(groupId);
        List<UserId> userIdList = new ArrayList<>();
        for(User user:users) {
            userIdList.add(user.getUserId());
        }
        if(ignoreList!=null)
            userIdList.removeAll(ignoreList);
        for(UserId userId:userIdList) {
            sendTextMessage(userId,text);
        }
    }

    @Override
    public void broadcastAll(String text) {
        List<User> users = dataBaseService.getAllUsers();
        for(User user:users) {
            sendTextMessage(user.getUserId(),text);
        }
    }

    @Override
    public void sendTextMessage(UserId userId, String textMessage) {
        IMService imService = getIMService(userId);
        if(imService==null) return;
        imService.sendTextMessage(userId.getInstantMessagingSoftwareUserId(),textMessage);
    }

    @Override
    public void sendButtonListMessage(UserId userId, ButtonList buttonList) {
        IMService imService = getIMService(userId);
        if(imService==null) return;
        imService.sendButtonListMessage(userId.getInstantMessagingSoftwareUserId(),buttonList);
    }
    public IMService getIMService(UserId userId){
        switch(userId.getInstantMessagingSoftware()) {
            case LINE-> {return lineMessageService;}
            case TELEGRAM-> {return telegramMessageService;}
        }
        return null;
    }
}
