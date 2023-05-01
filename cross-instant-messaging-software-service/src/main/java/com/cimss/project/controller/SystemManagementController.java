package com.cimss.project.controller;

import com.cimss.project.apibody.MessageBean;
import com.cimss.project.controller.exception.UnauthorizedException;
import com.cimss.project.database.*;
import com.cimss.project.database.entity.Group;
import com.cimss.project.database.entity.Member;
import com.cimss.project.database.entity.User;
import com.cimss.project.service.AuthorizationService;
import com.cimss.project.service.CIMSService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/all/delete")
    public void deleteAll(@RequestHeader(name = "Authorization") String accessToken){
        if(!authorizationService.authorization(accessToken).adminPermission())
            throw new UnauthorizedException();
        databaseService.deleteAllData();
    }

    @GetMapping("/users")
    public List<User> getUsers(@RequestHeader(name = "Authorization") String accessToken){
        if(!authorizationService.authorization(accessToken).adminPermission())
            throw new UnauthorizedException();
        return userService.getAllUsers();
    }

    @GetMapping("/groups")
    public List<Group> getGroups(@RequestHeader(name = "Authorization") String accessToken){
        if(!authorizationService.authorization(accessToken).adminPermission())
            throw new UnauthorizedException();
        return groupService.getAllGroups();
    }
    @GetMapping("/members")
    public List<Member> getMembers(@RequestHeader(name = "Authorization") String accessToken){
        if(!authorizationService.authorization(accessToken).adminPermission())
            throw new UnauthorizedException();
        return memberService.getAllMembers();
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/broadcast/text")
    public void broadcastAll(@RequestHeader(name = "Authorization") String accessToken,
                             @RequestBody MessageBean messageBean) {
        if(!authorizationService.authorization(accessToken).adminPermission())
            throw new UnauthorizedException();
        cimsService.broadcastAll(messageBean.getMessage());
    }
}
