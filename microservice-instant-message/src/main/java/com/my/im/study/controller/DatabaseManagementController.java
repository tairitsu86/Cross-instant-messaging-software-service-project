package com.my.im.study.controller;

import com.my.im.study.apibody.ManageBean;
import com.my.im.study.database.*;
import com.my.im.study.database.entity.Group;
import com.my.im.study.database.entity.Manager;
import com.my.im.study.database.entity.Member;
import com.my.im.study.database.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manage")
public class DatabaseManagementController {

    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private DatabaseService databaseService;

    @GetMapping("/deletealldata")
    public void deletealldata(@RequestHeader(name = "Authorization") String accessToken){
        databaseService.deleteAllData();
    }

    @PostMapping("/adduser")
    public User addUser(@RequestHeader(name = "Authorization") String accessToken,
                        @RequestBody ManageBean manageBean){
        return userService.createUser(new User(manageBean.getInstantMessagingSoftware(), manageBean.getInstantMessagingSoftwareUserId(), manageBean.getUserName()));
    }

    @PostMapping("/removeuser")
    public ManageBean removeUser(@RequestHeader(name = "Authorization") String accessToken,
                                 @RequestBody ManageBean manageBean){
        userService.deleteUser(manageBean.getInstantMessagingSoftware(), manageBean.getInstantMessagingSoftwareUserId());
        return manageBean;
    }

    @GetMapping("/users")
    public List<User> getUsers(@RequestHeader(name = "Authorization") String accessToken){
        return userService.getAllUsers();
    }

    @PostMapping("/addgroup")
    public Group addGroup(@RequestHeader(name = "Authorization") String accessToken,
                          @RequestBody ManageBean manageBean){
        return groupService.createGroup(Group.CreateGroup(manageBean.getGroupName(),null));
    }

    @PostMapping("/removegroup")
    public ManageBean removeGroup(@RequestHeader(name = "Authorization") String accessToken,
                                  @RequestBody ManageBean manageBean){
        groupService.deleteGroup(manageBean.getGroupId());
        return manageBean;
    }

    @GetMapping("/groups")
    public List<Group> getGroups(@RequestHeader(name = "Authorization") String accessToken){
        return groupService.getAllGroups();
    }

    @PostMapping("/addmanager")
    public ManageBean addManager(@RequestHeader(name = "Authorization") String accessToken,
                                 @RequestBody ManageBean manageBean){
        managerService.grantPermission(manageBean.getInstantMessagingSoftware(), manageBean.getInstantMessagingSoftwareUserId(), manageBean.getGroupId());
        return manageBean;
    }

    @PostMapping("/removemanager")
    public ManageBean removeManager(@RequestHeader(name = "Authorization") String accessToken,
                                    @RequestBody ManageBean manageBean){
        managerService.revokePermission(manageBean.getInstantMessagingSoftware(), manageBean.getInstantMessagingSoftwareUserId(), manageBean.getGroupId());
        return manageBean;
    }

    @GetMapping("/managers")
    public List<Manager> getManagers(@RequestHeader(name = "Authorization") String accessToken){
        return managerService.getAllManagers();
    }

    @PostMapping("/addmember")
    public ManageBean addMember(@RequestHeader(name = "Authorization") String accessToken,
                                @RequestBody ManageBean manageBean){
        memberService.join(manageBean.getInstantMessagingSoftware(), manageBean.getInstantMessagingSoftwareUserId(), manageBean.getGroupId());
        return manageBean;
    }

    @PostMapping("/removemember")
    public ManageBean removeMember(@RequestHeader(name = "Authorization") String accessToken,
                                   @RequestBody ManageBean manageBean){
        memberService.leave(manageBean.getInstantMessagingSoftware(), manageBean.getInstantMessagingSoftwareUserId(), manageBean.getGroupId());
        return manageBean;
    }

    @GetMapping("/members")
    public List<Member> getMembers(@RequestHeader(name = "Authorization") String accessToken){
        return memberService.getAllMembers();
    }
}
