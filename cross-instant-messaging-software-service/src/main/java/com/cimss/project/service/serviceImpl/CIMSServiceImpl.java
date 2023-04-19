package com.cimss.project.service.serviceImpl;


import com.cimss.project.database.GroupService;
import com.cimss.project.database.MemberService;
import com.cimss.project.linebot.LineMessageService;
import com.cimss.project.service.*;
import com.cimss.project.apibody.EventBean;
import com.cimss.project.database.ManagerService;
import com.cimss.project.database.UserService;
import com.cimss.project.database.entity.Group;
import com.cimss.project.database.entity.User;
import com.cimss.project.service.token.InstantMessagingSoftwareList;
import com.cimss.project.service.token.PermissionList;
import com.cimss.project.telegrambot.TelegramMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CIMSServiceImpl implements CIMSService {
    @Autowired
    private MemberService memberService;
    @Autowired
    private LineMessageService lineMessageService;
    @Autowired
    private TelegramMessageService telegramMessageService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private WebhookService webhookService;
    @Autowired
    private AuthorizationService authorizationService;

    @Override
    public String broadcast(String groupId,String text) {
        List<User> users = memberService.getUsers(groupId);
        for(User user:users) {
            sendTextMessage(user.getInstantMessagingSoftware(),user.getInstantMessagingSoftwareUserId(),text);
        }
        return "Broadcast done!";
    }

    @Override
    public String broadcastAll(String text) {
        List<User> users = userService.getAllUsers();
        for(User user:users) {
            sendTextMessage(user.getInstantMessagingSoftware(),user.getInstantMessagingSoftwareUserId(),text);
        }
        return "Broadcast to every user done!";
    }

    @Override
    public String sendTextMessage(String instantMessagingSoftware, String userId, String textMessage) {
        InstantMessagingSoftwareList i;
        try{
            i = InstantMessagingSoftwareList.valueOf(instantMessagingSoftware);
        }catch (IllegalArgumentException e){
            return "Instant messaging software not exist!";
        }
        switch(i) {
            case LINE-> lineMessageService.sendTextMessage(userId,textMessage);
            case TELEGRAM-> telegramMessageService.sendTextMessage(Long.valueOf(userId),textMessage);
        }
        return "Success";
    }

    @Override
    public User userRegister(String instantMessagingSoftware, String instantMessagingSoftwareUserId, String userName) {
        return userService.createUser(new User(instantMessagingSoftware,instantMessagingSoftwareUserId,userName));
    }

    @Override
    public String join(String instantMessagingSoftware, String instantMessagingSoftwareUserId, String groupId) {
        try{
            memberService.join(instantMessagingSoftware,instantMessagingSoftwareUserId,groupId);
        }catch (Exception e){
            return e.getMessage();
        }
        return "Success!";
    }

    @Override
    public String leave(String instantMessagingSoftware, String instantMessagingSoftwareUserId, String groupId) {
        try{
            memberService.leave(instantMessagingSoftware,instantMessagingSoftwareUserId,groupId);
        }catch (Exception e){
            return e.getMessage();
        }
        return "Success!";
    }

    @Override
    public String grantPermission(String instantMessagingSoftware, String instantMessagingSoftwareUserId, String groupId) {
        try{
            managerService.grantPermission(instantMessagingSoftware,instantMessagingSoftwareUserId,groupId);
        }catch (Exception e){
            return e.getMessage();
        }
        return "Success!";
    }

    @Override
    public Group newGroup(String groupName,String groupWebhook) {
        return groupService.createGroup(Group.CreateServiceGroup(groupName,groupWebhook));
    }

    @Override
    public String renameGroup(String groupId,String groupName) {
        Group group = groupService.getGroupById(groupId);
        if(group==null) return "Wrong group id!";
        group.setGroupName(groupName);
        return groupService.alterGroup(group);
    }

    @Override
    public String restateGroup(String groupId, String groupDescription) {
        Group group = groupService.getGroupById(groupId);
        if(group==null) return "Wrong group id!";
        group.setGroupDescription(groupDescription);
        return groupService.alterGroup(group);
    }

    @Override
    public String alterGroup(String groupId, String property, String value) {
        Group group = groupService.getGroupById(groupId);
        if(group==null) return "Wrong group id!";
        boolean val = true;
        switch(value){
            case "true" -> val = true;
            case "false" -> val = false;
            default -> {return "Wrong value type!";}
        }
        switch (property){
            case "isPublic" -> {
                group.setPublic(val);
            }
            case "joinById" -> {
                group.setJoinById(val);
            }
            case "allMessageBroadcast" -> {
                group.setAllMessageBroadcast(val);
            }
            default -> {
                return "Property not exist!";
            }
        }
        return groupService.alterGroup(group);
    }

    @Override
    public List<Group.GroupData> searchGroup(String groupName) {
        return groupService.getGroupByName(groupName);
    }

    @Override
    public void TextEventHandler(String instantMessagingSoftware, String instantMessagingSoftwareUserId, String text) {
        if(text.startsWith("/cimss")){
            String executeResult = CIMSSdecoder(instantMessagingSoftware, instantMessagingSoftwareUserId,text);
            sendTextMessage(instantMessagingSoftware,instantMessagingSoftwareUserId,executeResult);
//            webhookService.webhookSendEvent(instantMessagingSoftware,instantMessagingSoftwareUserId, EventBean.createTextCommandEventBean(instantMessagingSoftware,instantMessagingSoftwareUserId,text));
            return;
        }
        webhookService.webhookSendEvent(instantMessagingSoftware,instantMessagingSoftwareUserId, EventBean.createTextMessageEventBean(instantMessagingSoftware,instantMessagingSoftwareUserId,text));
    }
    public String CIMSSdecoder(String instantMessagingSoftware, String instantMessagingSoftwareUserId,String command){
        String commandType = command.split(" ")[1];
        String result;
        //switch case for no require permission command
        switch (commandType) {
            case "search" -> {
                String keyword = command.split(" ", 3)[2];
                List<Group.GroupData> searchResult = searchGroup(keyword);
                if(searchResult.size()==0) {
                    result = String.format("No found with key word \"%s\"!", keyword);
                    break;
                }
                result = String.format("Search for key word \"%s\":", keyword);
                for (Group.GroupData group : searchResult) {
                    result = String.format("%s\n\ngroup name:\n%s\ngroup id:\n%s", result, group.getGroupName(), group.getGroupId());
                }
            }
            case "newgroup" ->{
                String groupName = command.split(" ", 3)[2];
                Group newGroup;
                try {
                    newGroup = groupService.createGroup(Group.CreatePrivateGroup(groupName));
                    join(instantMessagingSoftware,instantMessagingSoftwareUserId,newGroup.getGroupId());
                    grantPermission(instantMessagingSoftware,instantMessagingSoftwareUserId,newGroup.getGroupId());
                }catch (Exception e){
                    result = String.format("Create Error with %s",e.getMessage());
                    break;
                }
                result = String.format("Create success,this is your group id:\n%s",newGroup.getGroupId());
            }
            case "join" -> result = memberService.joinWithProperty(instantMessagingSoftware, instantMessagingSoftwareUserId, command.split(" ", 3)[2]);
            case "leave" -> result = leave(instantMessagingSoftware, instantMessagingSoftwareUserId, command.split(" ", 3)[2]);
            case "groups" -> {
                List<Group> joinedGroup = memberService.getGroups(instantMessagingSoftware, instantMessagingSoftwareUserId);
                if(joinedGroup.size()==0){
                    result = "Didn't join any group now!";
                    break;
                }
                result = "The groups you joined:";
                for (Group group : joinedGroup) {
                    result = String.format("%s\n\ngroup name:\n%s\ngroup id:\n%s", result, group.getGroupName(), group.getGroupId());
                }
            }
            default ->{
                result = "Command no found or you don't have the permission!";
                if(CommandAuthorization(instantMessagingSoftware,instantMessagingSoftwareUserId,command)){
                    //switch case for require manager permission command
                    String groupId = command.split(" ")[2];
                    switch (commandType){
                        case "members"->{
                            List<User> users = memberService.getUsers(groupId);
                            result = String.format("Members in \"%s\":",groupService.getGroupById(groupId).getGroupName());
                            for(User user:users){
                                result = String.format("%s\n%s %s\n%s",result,user.getInstantMessagingSoftware(),user.getUserName(),user.getInstantMessagingSoftwareUserId());
                            }
                        }
                        case "broadcast"-> result = broadcast(groupId,command.split(" ",4)[3]);
                        case "remove"-> result = leave(command.split(" ",5)[3],command.split(" ",5)[4],groupId);
                        case "detail"->{
                            Group group = groupService.getGroupById(groupId);
                            result = String.format("%s\nDescription:%s\nIs public? %s\nCan joined this group by id? %s\nWill all message broadcast? %s",group.getGroupName(),group.getGroupDescription(),group.isPublic(),group.isJoinById(),group.isAllMessageBroadcast());
                        }
                        case "alter"->{
                            String property = command.split(" ",5)[3],value = command.split(" ",5)[4];
                            switch (property){
                                case "groupName"-> result = renameGroup(groupId,value);
                                case "groupDescription"-> result = restateGroup(groupId,value);
                                default -> result = alterGroup(groupId,property,value);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }
    public boolean CommandAuthorization(String instantMessagingSoftware, String instantMessagingSoftwareUserId,String command){
        String cmd[] = command.split(" ");
        if(cmd.length<3) return false;
        PermissionList returnToken = authorizationService.authorization(instantMessagingSoftwareUserId,instantMessagingSoftware,command.split(" ")[2]);
        System.err.println(returnToken);
        return returnToken.managerPermission();
    }

}
