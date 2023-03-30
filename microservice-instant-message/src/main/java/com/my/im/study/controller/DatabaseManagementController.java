package com.my.im.study.controller;

import com.my.im.study.apibody.ManageBody;
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
                        @RequestBody ManageBody manageBody){
        return userService.createUser(new User(manageBody.getInstantMessagingSoftware(),manageBody.getInstantMessagingSoftwareUserId(),manageBody.getUserName()));
    }

    @PostMapping("/removeuser")
    public ManageBody removeUser(@RequestHeader(name = "Authorization") String accessToken,
                                 @RequestBody ManageBody manageBody){
        userService.deleteUser(manageBody.getInstantMessagingSoftware(),manageBody.getInstantMessagingSoftwareUserId());
        return manageBody;
    }

    @GetMapping("/users")
    public List<User> getUsers(@RequestHeader(name = "Authorization") String accessToken){
        return userService.getAllUsers();
    }

    @PostMapping("/addgroup")
    public Group addGroup(@RequestHeader(name = "Authorization") String accessToken,
                          @RequestBody ManageBody manageBody){
        return groupService.createGroup(new Group(null,manageBody.getGroupName()));
    }

    @PostMapping("/removegroup")
    public ManageBody removeGroup(@RequestHeader(name = "Authorization") String accessToken,
                                  @RequestBody ManageBody manageBody){
        groupService.deleteGroup(manageBody.getGroupId());
        return manageBody;
    }

    @GetMapping("/groups")
    public List<Group> getGroups(@RequestHeader(name = "Authorization") String accessToken){
        return groupService.getAllGroups();
    }

    @PostMapping("/addmanager")
    public ManageBody addManager(@RequestHeader(name = "Authorization") String accessToken,
                                 @RequestBody ManageBody manageBody){
        managerService.grantPermission(manageBody.getInstantMessagingSoftware(), manageBody.getInstantMessagingSoftwareUserId(), manageBody.getGroupId());
        return manageBody;
    }

    @PostMapping("/removemanager")
    public ManageBody removeManager(@RequestHeader(name = "Authorization") String accessToken,
                                    @RequestBody ManageBody manageBody){
        managerService.revokePermission(manageBody.getInstantMessagingSoftware(), manageBody.getInstantMessagingSoftwareUserId(), manageBody.getGroupId());
        return manageBody;
    }

    @GetMapping("/managers")
    public List<Manager> getManagers(@RequestHeader(name = "Authorization") String accessToken){
        return managerService.getAllManagers();
    }

    @PostMapping("/addmember")
    public ManageBody addMember(@RequestHeader(name = "Authorization") String accessToken,
                                @RequestBody ManageBody manageBody){
        memberService.join(manageBody.getInstantMessagingSoftware(), manageBody.getInstantMessagingSoftwareUserId(), manageBody.getGroupId());
        return manageBody;
    }

    @PostMapping("/removemember")
    public ManageBody removeMember(@RequestHeader(name = "Authorization") String accessToken,
                                   @RequestBody ManageBody manageBody){
        memberService.leave(manageBody.getInstantMessagingSoftware(), manageBody.getInstantMessagingSoftwareUserId(), manageBody.getGroupId());
        return manageBody;
    }

    @GetMapping("/members")
    public List<Member> getMembers(@RequestHeader(name = "Authorization") String accessToken){
        return memberService.getAllMembers();
    }
}
