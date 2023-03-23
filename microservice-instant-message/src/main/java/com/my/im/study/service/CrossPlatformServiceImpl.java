package com.my.im.study.service;


import com.my.im.study.database.GroupService;
import com.my.im.study.database.ManagerService;
import com.my.im.study.database.MemberService;
import com.my.im.study.database.UserService;
import com.my.im.study.database.entity.Group;
import com.my.im.study.database.entity.User;
import com.my.im.study.linebot.LineMessageService;
import com.my.im.study.telegrambot.TelegramMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrossPlatformServiceImpl implements CrossPlatformService {
    @Autowired
    private MemberService memberService;
    @Autowired
    private LineMessageService lineMessageService;
    @Autowired
    private TelegramMessageService telegramMessageService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;

    @Override
    public String broadcast(String groupId,String text) {
        List<User> users = memberService.getUsers(groupId);
        for(User user:users) {
            switch(InstantMessagingSoftwareList.valueOf(user.getInstantMessagingSoftware())) {
                case LINE:
                    lineMessageService.pushTextMessage(user.getInstantMessagingSoftwareUserId(),text);
                    break;
                case TELEGRAM:
                    telegramMessageService.sendTextMessage(Long.parseLong(user.getInstantMessagingSoftwareUserId()), text);
                    break;
            }
        }
        return "Broadcast done!";
    }

    @Override
    public String broadcastAll(String text) {
        List<User> users = userService.getAllUsers();
        for(User user:users) {
            switch(InstantMessagingSoftwareList.valueOf(user.getInstantMessagingSoftware())) {
                case LINE:
                    lineMessageService.pushTextMessage(user.getInstantMessagingSoftwareUserId(),text);
                    break;
                case TELEGRAM:
                    telegramMessageService.sendTextMessage(Long.parseLong(user.getInstantMessagingSoftwareUserId()), text);
                    break;
            }
        }
        return "Broadcast to every user done!";
    }

    @Override
    public String sendTextMessage(String platform, String userid, String textMessage) {
        InstantMessagingSoftwareList instantMessagingSoftware = InstantMessagingSoftwareList.valueOf(platform);
        if(instantMessagingSoftware==null) return "Wrong platform!";
        switch(instantMessagingSoftware) {
            case LINE:
                lineMessageService.pushTextMessage(userid,textMessage);
                break;
            case TELEGRAM:
                telegramMessageService.sendTextMessage(Long.valueOf(userid),textMessage);
                break;
        }
        return "Success";
    }

    @Override
    public User userRegister(String instantMessagingSoftware, String instantMessagingSoftwareUserId, String userName) {
        return userService.createUser(new User(instantMessagingSoftware,instantMessagingSoftwareUserId,userName));
    }

    @Override
    public String join(String instantMessagingSoftware, String instantMessagingSoftwareUserId, String groupId) {
        try{
            memberService.join(instantMessagingSoftware,instantMessagingSoftwareUserId,groupId);
        }catch (Exception e){
            return e.getMessage();
        }
        return "Success!";
    }

    @Override
    public Group newGroup(String groupName) {
        return groupService.createGroup(new Group(groupName));
    }


}
