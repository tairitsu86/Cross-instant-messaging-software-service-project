package com.cimss.project.controller;

import com.cimss.project.apibody.MessageBean;
import com.cimss.project.database.entity.Group;
import com.cimss.project.database.entity.Member;
import com.cimss.project.database.entity.User;
import com.cimss.project.handler.APIHandler;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Hidden
@RestController
@RequestMapping("/manage")
public class SystemManagementController {

    @Autowired
    private APIHandler apiHandler;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/all/delete")
    public void deleteAll(@RequestHeader(name = "Authorization") String accessToken){
        apiHandler.deleteAll(accessToken);
    }

    @GetMapping("/users")
    public List<User> getUsers(@RequestHeader(name = "Authorization") String accessToken){
        return apiHandler.getUsers(accessToken);
    }

    @GetMapping("/groups")
    public List<Group> getGroups(@RequestHeader(name = "Authorization") String accessToken){
        return apiHandler.getGroups(accessToken);
    }
    @GetMapping("/members")
    public List<Member> getMembers(@RequestHeader(name = "Authorization") String accessToken){
        return apiHandler.getMembers(accessToken);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/broadcast/text")
    public void broadcastAll(@RequestHeader(name = "Authorization") String accessToken,
                             @Valid @RequestBody MessageBean messageBean) {
        apiHandler.broadcastAll(accessToken,messageBean);
    }
}
