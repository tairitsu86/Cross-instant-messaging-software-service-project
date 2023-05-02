package com.cimss.project.service;

import com.cimss.project.database.entity.Group;
import com.cimss.project.database.entity.Member;
import com.cimss.project.database.entity.User;
import com.cimss.project.database.entity.UserId;

import java.util.List;

public interface CIMSService {

    String broadcast(String groupId,String text);

    String broadcastAll(String text);

    String sendTextMessage(UserId userId, String textMessage);

    User userRegister(UserId userId, String userName);

    String join(UserId userId,String groupId);
    String joinWithProperty(UserId userId,String groupId);

    String leave(UserId userId, String groupId);

    String grantPermission(UserId userId, String groupId);

    String revokePermission(UserId userId, String groupId);

    Group newGroup(Group group);

    String alterGroup(String groupId,String property,String value);

    String alterGroup(Group group);

    Group groupDetail(String groupId);

    String deleteGroup(String groupId);

    List<Group.GroupData> searchGroup(String groupName);
    List<Member.MemberData> getMembers(String groupId);
    List<Group> getGroups(UserId userId);
    User getUserById(UserId userId);
    Group getGroupById(String groupId);
    String getGroupIdByAuthorizationKey(String authorizationKey);
    boolean isMember(UserId userId, String groupId);
    boolean isManager(UserId userId,String groupId);
    boolean isUserExist(UserId userId);

}
