package com.cimss.project.controller;

import com.cimss.project.apibody.MessageBean;
import com.cimss.project.database.*;
import com.cimss.project.database.entity.Group;
import com.cimss.project.database.entity.Member;
import com.cimss.project.database.entity.User;
import com.cimss.project.service.AuthorizationService;
import com.cimss.project.service.CIMSService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Hidden
@RestController
@RequestMapping("/manage")
public class SystemManagementController {

    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private DatabaseService databaseService;
    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private CIMSService cimsService;

    @GetMapping("/deletealldata")
    public MessageBean deletealldata(@RequestHeader(name = "Authorization") String accessToken){
        if(!authorizationService.authorization(accessToken).adminPermission()) return MessageBean.CreateAuthorizationWrongMessageBean();
        databaseService.deleteAllData();
        return MessageBean.CreateMessageBean("Success");
    }

    @GetMapping("/users")
    public List<User> getUsers(@RequestHeader(name = "Authorization") String accessToken){
        if(!authorizationService.authorization(accessToken).adminPermission()) return null;
        return userService.getAllUsers();
    }

    @GetMapping("/groups")
    public List<Group> getGroups(@RequestHeader(name = "Authorization") String accessToken){
        if(!authorizationService.authorization(accessToken).adminPermission()) return null;
        return groupService.getAllGroups();
    }
    @GetMapping("/members")
    public List<Member> getMembers(@RequestHeader(name = "Authorization") String accessToken){
        if(!authorizationService.authorization(accessToken).adminPermission()) return null;
        return memberService.getAllMembers();
    }
    @GetMapping("/broadcastall")
    public MessageBean broadcastAll(@RequestHeader(name = "Authorization") String accessToken,
                                    @RequestParam String message) {
        if(!authorizationService.authorization(accessToken).adminPermission()) return MessageBean.CreateAuthorizationWrongMessageBean();
        return MessageBean.CreateMessageBean(cimsService.broadcastAll(message));
    }
}
