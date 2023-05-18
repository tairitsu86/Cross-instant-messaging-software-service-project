package com.cimss.project.database;

import com.cimss.project.database.entity.*;
import com.cimss.project.database.entity.token.GroupRole;

import java.util.List;

public interface DatabaseService {
    void deleteAllData();
    Group createGroup(Group group);
    String alterGroup(Group group);
    Group getGroupById(String groupId);
    List<Group.GroupData> getGroupByName(String groupName);
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
    String alterPermission(UserId userId, String groupId, GroupRole groupRole);
    List<User> getUsers(String groupId);
    List<Member.MemberData> getMembers(String groupId);
    List<Group> getGroups(UserId userId);
    List<Member> getAllMembers();
    void deleteAllMembers();
    boolean isMember(UserId userId, String groupId);
    Member getMemberById(MemberId memberId);
    List<String> getRoles(UserId userId);
}
