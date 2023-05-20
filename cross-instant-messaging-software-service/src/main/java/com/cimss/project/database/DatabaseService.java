package com.cimss.project.database;

import com.cimss.project.database.entity.*;
import com.cimss.project.database.entity.token.GroupRole;

import java.util.List;

public interface DatabaseService {
    void deleteAllData();
    Group createGroup(Group group);
    void alterGroup(Group group);
    Group getGroupById(String groupId);
    List<Group.GroupData> getGroupByName(String groupName);
    List<Group> getAllGroups();
    void deleteGroup(String groupId);
    void deleteAllGroups();
    User createUser(User user);
    User getUserById(UserId userId);
    List<User> getAllUsers();
    void deleteAllUsers();
    boolean isUserExist(UserId userId);
    void join(UserId userId,String groupId);
    void joinWithProperty(UserId userId,String groupId);
    void leave(UserId userId,String groupId);
    void alterPermission(UserId userId, String groupId, GroupRole groupRole);
    List<User> getUsers(String groupId);
    List<Member.MemberData> getMembers(String groupId);
    List<Group> getGroups(UserId userId);
    List<Member> getAllMembers();
    void deleteAllMembers();
    boolean isMember(UserId userId, String groupId);
    Member getMemberById(MemberId memberId);
    List<String> getRoles(UserId userId);
}
