package com.cimss.project.service.serviceImpl;


import com.cimss.project.database.GroupService;
import com.cimss.project.database.MemberService;
import com.cimss.project.linebot.LineMessageService;
import com.cimss.project.service.*;
import com.cimss.project.database.UserService;
import com.cimss.project.database.entity.Group;
import com.cimss.project.database.entity.User;
import com.cimss.project.service.token.InstantMessagingSoftwareList;
import com.cimss.project.telegrambot.TelegramMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CIMSServiceImpl implements CIMSService {
    @Autowired
    private MemberService memberService;
    @Autowired
    private LineMessageService lineMessageService;
    @Autowired
    private TelegramMessageService telegramMessageService;
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
            case LINE-> lineMessageService.sendTextMessage(userId,textMessage);
            case TELEGRAM-> telegramMessageService.sendTextMessage(Long.valueOf(userId),textMessage);
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
    public String leave(String instantMessagingSoftware, String instantMessagingSoftwareUserId, String groupId) {
        try{
            memberService.leave(instantMessagingSoftware,instantMessagingSoftwareUserId,groupId);
        }catch (Exception e){
            return e.getMessage();
        }
        return "Success!";
    }

    @Override
    public String grantPermission(String instantMessagingSoftware, String instantMessagingSoftwareUserId, String groupId) {
        try{
            memberService.grantPermission(instantMessagingSoftware,instantMessagingSoftwareUserId,groupId);
        }catch (Exception e){
            return e.getMessage();
        }
        return "Success!";
    }

    @Override
    public String revokePermission(String instantMessagingSoftware, String instantMessagingSoftwareUserId, String groupId) {
        try{
            memberService.revokePermission(instantMessagingSoftware,instantMessagingSoftwareUserId,groupId);
        }catch (Exception e){
            return e.getMessage();
        }
        return "Success!";
    }

    @Override
    public Group newGroup(Group group) {
        return groupService.createGroup(group);
    }

    @Override
    public String alterGroup(String groupId, String property, String value) {
        Group group = Group.CreateEditGroup(groupId);
        Boolean val = null;
        switch(value){
            case "true" -> val = true;
            case "false" -> val = false;
            default ->{
                if(!(property.equals("groupName")||property.equals("groupDescription")))
                    return "Alter command error!";
            }
        }
        switch (property){
            case "groupName"-> group.setGroupName(value);
            case "groupDescription"-> group.setGroupDescription(value);
            case "isPublic" -> group.setIsPublic(val);
            case "joinById" -> group.setJoinById(val);
            default -> {return "Property not exist!";}
        }
        return alterGroup(group);
    }

    @Override
    public String alterGroup(Group group) {
        return groupService.alterGroup(group);
    }

    @Override
    public Group.GroupDetail groupDetail(String groupId) {
        return Group.CreateDetailBean(groupService.getGroupById(groupId));
    }

    @Override
    public String deleteGroup(String groupId) {
        memberService.deleteAllMembers();
        return groupService.deleteGroup(groupId);
    }

    @Override
    public List<Group.GroupData> searchGroup(String groupName) {
        return groupService.getGroupByName(groupName);
    }

    @Override
    public List<User> getMembers(String groupId) {
        return memberService.getUsers(groupId);
    }

}
