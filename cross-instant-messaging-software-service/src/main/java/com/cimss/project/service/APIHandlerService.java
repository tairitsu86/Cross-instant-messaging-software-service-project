package com.cimss.project.service;

import com.cimss.project.apibody.ManageBean;
import com.cimss.project.apibody.MessageBean;
import com.cimss.project.controller.exception.RequestNotFoundException;
import com.cimss.project.database.entity.Group;
import com.cimss.project.database.entity.Member;
import com.cimss.project.database.entity.User;
import com.cimss.project.database.entity.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface APIHandlerService {
    
    ResponseEntity<MessageBean> homeTest();
    
    List<Group.GroupData> searchGroup(String keyword);
    
    Group newGroup(ManageBean.NewGroupBean newGroupBean);

    List<Member.MemberData> getMembers(String accessToken,String groupId);
    
    Group groupDetail(String accessToken,String groupId);
    
    void sendTextMessage(String accessToken,ManageBean.SendBean sendBean);
    
    void webhookTest(String accessToken,String groupId);

    void broadcast(String accessToken,ManageBean.BroadcastBean broadcastBean);

    void alterGroup(String accessToken,ManageBean.AlterGroupBean alterGroupBean);

    void grantPermission(String accessToken,ManageBean.GrantPermissionBean grantPermissionBean);

    void revokePermission(String accessToken,ManageBean.GrantPermissionBean grantPermissionBean);

    void deleteGroup(String accessToken, String groupId);

    void deleteAll(String accessToken);

    List<User> getUsers(String accessToken);

    List<Group> getGroups(String accessToken);

    List<Member> getMembers(String accessToken);

    void broadcastAll(String accessToken,MessageBean messageBean);
    
}
