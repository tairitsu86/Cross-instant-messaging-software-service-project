package com.cimss.project.database;

import com.cimss.project.database.entity.Group;
import com.cimss.project.database.entity.Member;
import com.cimss.project.database.entity.User;
import com.cimss.project.database.entity.UserId;

import java.util.List;

public interface DatabaseService {
    void deleteAllData();
    Group createGroup(Group group);
    String alterGroup(Group group);
    Group getGroupById(String groupId);
    List<Group.GroupData> getGroupByName(String groupName);
    String getGroupIdByAuthorizationKey(String authorizationKey);
    List<Group> getAllGroups();
    String deleteGroup(String groupId);
    void deleteAllGroups();
    User createUser(User user);
    User getUserById(UserId userId);
    List<User> getAllUsers();
    void deleteAllUsers();
    boolean isUserExist(UserId userId);
    String join(UserId userId,String groupId);
    String joinWithProperty(UserId userId,String groupId);
    String leave(UserId userId,String groupId);
    String grantPermission(UserId userId,String groupId);
    String revokePermission(UserId userId,String groupId);
    List<User> getUsers(String groupId);
    List<Member.MemberData> getMembers(String groupId);
    List<Group> getGroups(UserId userId);
    List<Member> getAllMembers();
    void deleteAllMembers();
    boolean isMember(UserId userId, String groupId);
    boolean isManager(UserId userId,String groupId);
}
