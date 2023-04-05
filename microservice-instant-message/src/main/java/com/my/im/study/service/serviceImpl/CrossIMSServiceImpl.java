package com.my.im.study.service.serviceImpl;


import com.my.im.study.database.GroupService;
import com.my.im.study.database.ManagerService;
import com.my.im.study.database.MemberService;
import com.my.im.study.database.UserService;
import com.my.im.study.database.entity.Group;
import com.my.im.study.database.entity.User;
import com.my.im.study.linebot.LineMessageService;
import com.my.im.study.service.CrossIMSService;
import com.my.im.study.service.InstantMessagingSoftwareList;
import com.my.im.study.telegrambot.TelegramMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrossIMSServiceImpl implements CrossIMSService {
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
            sendTextMessage(user.getInstantMessagingSoftware(),user.getInstantMessagingSoftwareUserId(),text);
        }
        return "Broadcast done!";
    }

    @Override
    public String broadcastAll(String text) {
        List<User> users = userService.getAllUsers();
        for(User user:users) {
            sendTextMessage(user.getInstantMessagingSoftware(),user.getInstantMessagingSoftwareUserId(),text);
        }
        return "Broadcast to every user done!";
    }

    @Override
    public String sendTextMessage(String instantMessagingSoftware, String userId, String textMessage) {
        InstantMessagingSoftwareList i;
        try{
            i = InstantMessagingSoftwareList.valueOf(instantMessagingSoftware);
        }catch (IllegalArgumentException e){
            return "Instant messaging software not exist!";
        }
        switch(i) {
            case LINE:
                lineMessageService.pushTextMessage(userId,textMessage);
                break;
            case TELEGRAM:
                telegramMessageService.sendTextMessage(Long.valueOf(userId),textMessage);
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
    public String grantPermission(String instantMessagingSoftware, String instantMessagingSoftwareUserId, String groupId) {
        try{
            managerService.grantPermission(instantMessagingSoftware,instantMessagingSoftwareUserId,groupId);
        }catch (Exception e){
            return e.getMessage();
        }
        return "Success!";
    }

    @Override
    public Group newGroup(String groupName,String groupWebhook) {
        return groupService.createGroup(Group.CreateGroup(groupName,groupWebhook));
    }

    @Override
    public List<Group> searchGroup(String groupName) {
        return groupService.getGroupByName(groupName);
    }


}
