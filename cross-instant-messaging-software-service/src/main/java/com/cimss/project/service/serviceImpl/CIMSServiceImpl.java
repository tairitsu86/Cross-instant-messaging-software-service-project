package com.cimss.project.service.serviceImpl;


import com.cimss.project.database.DatabaseService;
import com.cimss.project.database.entity.Member;
import com.cimss.project.database.entity.UserId;
import com.cimss.project.im.IMService;
import com.cimss.project.service.*;
import com.cimss.project.database.entity.Group;
import com.cimss.project.database.entity.User;
import com.cimss.project.service.token.InstantMessagingSoftwareList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
    public String broadcast(String groupId,String text,List<UserId> ignoreList) {
        List<User> users = dataBaseService.getUsers(groupId);
        if(ignoreList!=null)
            users.removeAll(ignoreList);
        for(User user:users) {
            sendTextMessage(user.toUserId(),text);
        }
        return "Broadcast done!";
    }

    @Override
    public String broadcastAll(String text) {
        List<User> users = dataBaseService.getAllUsers();
        for(User user:users) {
            sendTextMessage(user.toUserId(),text);
        }
        return "Broadcast to every user done!";
    }

    @Override
    public String sendTextMessage(UserId userId, String textMessage) {
        switch(InstantMessagingSoftwareList.getInstantMessagingSoftwareToken(userId.getInstantMessagingSoftware())) {
            case LINE-> lineMessageService.sendTextMessage(userId.getInstantMessagingSoftwareUserId(),textMessage);
            case TELEGRAM-> telegramMessageService.sendTextMessage(userId.getInstantMessagingSoftwareUserId(),textMessage);
        }
        return "Success";
    }

    @Override
    public User userRegister(UserId userId, String userName) {
        return dataBaseService.createUser(User.CreateByUserId(userId,userName));
    }

    @Override
    public String join(UserId userId, String groupId) {
        try{
            dataBaseService.join(userId,groupId);
        }catch (Exception e){
            return e.getMessage();
        }
        return "Success!";
    }

    @Override
    public String joinWithProperty(UserId userId, String groupId) {
        return dataBaseService.joinWithProperty(userId,groupId);
    }

    @Override
    public String leave(UserId userId, String groupId) {
        try{
            dataBaseService.leave(userId,groupId);
        }catch (Exception e){
            return e.getMessage();
        }
        return "Success!";
    }

    @Override
    public String grantPermission(UserId userId, String groupId) {
        try{
            dataBaseService.grantPermission(userId,groupId);
        }catch (Exception e){
            return e.getMessage();
        }
        return "Success!";
    }

    @Override
    public String revokePermission(UserId userId, String groupId) {
        try{
            dataBaseService.revokePermission(userId,groupId);
        }catch (Exception e){
            return e.getMessage();
        }
        return "Success!";
    }

    @Override
    public Group newGroup(Group group) {
        return dataBaseService.createGroup(group);
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
        return dataBaseService.alterGroup(group);
    }

    @Override
    public Group groupDetail(String groupId) {
        return dataBaseService.getGroupById(groupId);
    }

    @Override
    public String deleteGroup(String groupId) {
        dataBaseService.deleteAllMembers();
        return dataBaseService.deleteGroup(groupId);
    }

    @Override
    public List<Group.GroupData> searchGroup(String groupName) {
        return dataBaseService.getGroupByName(groupName);
    }

    @Override
    public List<Member.MemberData> getMembers(String groupId) {
        return dataBaseService.getMembers(groupId);
    }

    @Override
    public List<Group> getGroups(UserId userId) {
        return dataBaseService.getGroups(userId);
    }

    @Override
    public User getUserById(UserId userId) {
        return dataBaseService.getUserById(userId);
    }

    @Override
    public Group getGroupById(String groupId) {
        return dataBaseService.getGroupById(groupId);
    }

    @Override
    public String getGroupIdByAuthorizationKey(String authorizationKey) {
        return dataBaseService.getGroupIdByAuthorizationKey(authorizationKey);
    }

    @Override
    public boolean isMember(UserId userId, String groupId) {
        return dataBaseService.isMember(userId,groupId);
    }

    @Override
    public boolean isManager(UserId userId, String groupId) {
        return dataBaseService.isManager(userId,groupId);
    }

    @Override
    public boolean isUserExist(UserId userId) {
        return dataBaseService.isUserExist(userId);
    }

}
