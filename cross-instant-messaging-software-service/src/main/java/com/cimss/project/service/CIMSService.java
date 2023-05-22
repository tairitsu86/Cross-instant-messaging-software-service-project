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

    User userRegister(UserId userId, String userName);
    void alterUserName(UserId userId, String userName);

    void join(UserId userId,String groupId);
    void joinWithProperty(UserId userId,String groupId);

    void leave(UserId userId, String groupId);

    void grantPermission(UserId userId, String groupId);

    void revokePermission(UserId userId, String groupId);
    void alterPermission(UserId userId, String groupId, GroupRole groupRole);

    Group newGroup(Group group);

    void alterGroupName(String groupId,String data);
    void alterGroupDescription(String groupId,String data);
    void alterGroupIsPublic(String groupId,boolean data);
    void alterGroupJoinById(String groupId,boolean data);

    void alterGroup(Group group);

    Group groupDetail(String groupId);

    void deleteGroup(String groupId);

    List<Group.GroupData> searchGroup(String groupName);
    List<Member.MemberData> getMembers(String groupId);
    List<Group> getGroups(UserId userId);
    User getUserById(UserId userId);
    Group getGroupById(String groupId);
    boolean isMember(UserId userId, String groupId);
    boolean isUserExist(UserId userId);
    Member getMemberById(MemberId memberId);
    GroupRole getGroupRole(UserId userId,String groupId);

}
