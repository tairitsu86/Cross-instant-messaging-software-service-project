package com.cimss.project.database.serviceImpl;

import com.cimss.project.database.*;
import com.cimss.project.database.entity.*;
import com.cimss.project.database.entity.token.GroupRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseServiceImpl implements DatabaseService {

    @Autowired
    private GroupService groupService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private UserService userService;

    @Override
    public void deleteAllData() {
        memberService.deleteAllMembers();
        userService.deleteAllUsers();
        groupService.deleteAllGroups();
    }

    @Override
    public Group createGroup(Group group) {
        return groupService.createGroup(group);
    }

    @Override
    public void alterGroup(Group group) {
        groupService.alterGroup(group);
    }

    @Override
    public Group getGroupById(String groupId) {
        return groupService.getGroupById(groupId);
    }

    @Override
    public List<Group.GroupData> getGroupByName(String groupName) {
        return groupService.getGroupByName(groupName);
    }



    @Override
    public List<Group> getAllGroups() {
        return groupService.getAllGroups();
    }

    @Override
    public void deleteGroup(String groupId) {
        groupService.deleteGroup(groupId);
    }

    @Override
    public void deleteAllGroups() {
        groupService.deleteAllGroups();
    }

    @Override
    public User createUser(User user) {
        return userService.createUser(user);
    }

    @Override
    public User getUserById(UserId userId) {
        return userService.getUserById(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Override
    public void deleteAllUsers() {
        userService.deleteAllUsers();
    }

    @Override
    public boolean isUserExist(UserId userId) {
        return userService.isUserExist(userId);
    }

    @Override
    public void join(UserId userId, String groupId) {
        memberService.join(userId,groupId);
    }

    @Override
    public void joinWithProperty(UserId userId, String groupId) {
        memberService.joinWithProperty(userId,groupId);
    }

    @Override
    public void leave(UserId userId, String groupId) {
        memberService.leave(userId,groupId);
    }

    @Override
    public void alterPermission(UserId userId, String groupId, GroupRole groupRole) {
        memberService.alterPermission(userId,groupId, groupRole);
    }


    @Override
    public List<User> getUsers(String groupId) {
        return memberService.getUsers(groupId);
    }

    @Override
    public List<Member.MemberData> getMembers(String groupId) {
        return memberService.getMembers(groupId);
    }

    @Override
    public List<Group> getGroups(UserId userId) {
        return memberService.getGroups(userId);
    }

    @Override
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    @Override
    public void deleteAllMembers() {
        memberService.deleteAllMembers();
    }

    @Override
    public boolean isMember(UserId userId, String groupId) {
        return memberService.isMember(userId,groupId);
    }

    @Override
    public Member getMemberById(MemberId memberId) {
        return memberService.getMemberById(memberId);
    }

    @Override
    public List<String> getRoles(UserId userId) {
        return memberService.getRoles(userId);
    }


}
