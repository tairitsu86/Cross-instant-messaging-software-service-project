package com.cimss.project.controller;

import com.cimss.project.apibody.MessageBean;
import com.cimss.project.database.*;
import com.cimss.project.database.entity.Group;
import com.cimss.project.database.entity.Member;
import com.cimss.project.database.entity.User;
import com.cimss.project.service.AuthorizationService;
import com.cimss.project.database.entity.Manager;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Hidden
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
    @Autowired
    private AuthorizationService authorizationService;

    @GetMapping("/deletealldata")
    public MessageBean deletealldata(@RequestHeader(name = "Authorization") String accessToken){
        if(!authorizationService.authorization(accessToken)) return MessageBean.CreateAuthorizationWrongMessageBean();
        databaseService.deleteAllData();
        return MessageBean.CreateMessageBean("Success");
    }

    @GetMapping("/users")
    public List<User> getUsers(@RequestHeader(name = "Authorization") String accessToken){
        if(!authorizationService.authorization(accessToken)) return null;
        return userService.getAllUsers();
    }

    @GetMapping("/groups")
    public List<Group> getGroups(@RequestHeader(name = "Authorization") String accessToken){
        if(!authorizationService.authorization(accessToken)) return null;
        return groupService.getAllGroups();
    }

    @GetMapping("/managers")
    public List<Manager> getManagers(@RequestHeader(name = "Authorization") String accessToken){
        if(!authorizationService.authorization(accessToken)) return null;
        return managerService.getAllManagers();
    }

    @GetMapping("/members")
    public List<Member> getMembers(@RequestHeader(name = "Authorization") String accessToken){
        if(!authorizationService.authorization(accessToken)) return null;
        return memberService.getAllMembers();
    }
}
