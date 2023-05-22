package com.cimss.project.service.serviceImpl;


import com.cimss.project.database.DatabaseService;
import com.cimss.project.database.entity.*;
import com.cimss.project.database.entity.token.GroupRole;
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
        switch(userId.getInstantMessagingSoftware()) {
            case LINE-> lineMessageService.sendTextMessage(userId.getInstantMessagingSoftwareUserId(),textMessage);
            case TELEGRAM-> telegramMessageService.sendTextMessage(userId.getInstantMessagingSoftwareUserId(),textMessage);
        }
    }

    @Override
    public void sendButtonListMessage(UserId userId, ButtonList buttonList) {
        switch(userId.getInstantMessagingSoftware()) {
            case LINE-> lineMessageService.sendButtonListMessage(userId.getInstantMessagingSoftwareUserId(),buttonList);
            case TELEGRAM-> telegramMessageService.sendButtonListMessage(userId.getInstantMessagingSoftwareUserId(),buttonList);
        }
    }

    @Override
    public User userRegister(UserId userId, String userName) {
        return dataBaseService.createUser(User.CreateUser(userId,userName));
    }

    @Override
    public void alterUserName(UserId userId, String userName) {
        User user = dataBaseService.getUserById(userId);
        if(user==null) return;
        user.setUserDisplayName(userName);
        dataBaseService.createUser(user);
    }

    @Override
    public void join(UserId userId, String groupId) {
        dataBaseService.join(userId,groupId);
    }

    @Override
    public void joinWithProperty(UserId userId, String groupId) {
        dataBaseService.joinWithProperty(userId,groupId);
    }

    @Override
    public void leave(UserId userId, String groupId) {
        dataBaseService.leave(userId,groupId);
    }

    @Override
    public void grantPermission(UserId userId, String groupId) {
        dataBaseService.alterPermission(userId,groupId, GroupRole.GROUP_MANAGER);
    }

    @Override
    public void revokePermission(UserId userId, String groupId) {
        dataBaseService.alterPermission(userId,groupId, GroupRole.GROUP_MEMBER);
    }
    @Override
    public void alterPermission(UserId userId, String groupId, GroupRole groupRole) {
        dataBaseService.alterPermission(userId,groupId, groupRole);
    }

    @Override
    public Group newGroup(Group group) {
        return dataBaseService.createGroup(group);
    }

    @Override
    public void alterGroupName(String groupId, String data) {
        Group group = Group.CreateEditGroup(groupId);
        group.setGroupName(data);
        dataBaseService.alterGroup(group);
    }

    @Override
    public void alterGroupDescription(String groupId, String data) {
        Group group = Group.CreateEditGroup(groupId);
        group.setGroupDescription(data);
        dataBaseService.alterGroup(group);
    }

    @Override
    public void alterGroupIsPublic(String groupId, boolean data) {
        Group group = Group.CreateEditGroup(groupId);
        group.setIsPublic(data);
        dataBaseService.alterGroup(group);
    }

    @Override
    public void alterGroupJoinById(String groupId, boolean data) {
        Group group = Group.CreateEditGroup(groupId);
        group.setJoinById(data);
        dataBaseService.alterGroup(group);
    }

    @Override
    public void alterGroup(Group group) {
        dataBaseService.alterGroup(group);
    }

    @Override
    public Group groupDetail(String groupId) {
        return dataBaseService.getGroupById(groupId);
    }

    @Override
    public void deleteGroup(String groupId) {
        dataBaseService.deleteGroup(groupId);
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
    public boolean isMember(UserId userId, String groupId) {
        return dataBaseService.isMember(userId,groupId);
    }


    @Override
    public boolean isUserExist(UserId userId) {
        return dataBaseService.isUserExist(userId);
    }

    @Override
    public Member getMemberById(MemberId memberId) {
        return null;
    }

    @Override
    public GroupRole getGroupRole(UserId userId, String groupId) {
        return dataBaseService.getMemberById(MemberId.CreateMemberId(userId,groupId)).getGroupRole();
    }

}
