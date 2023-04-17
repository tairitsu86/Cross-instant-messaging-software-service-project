package com.cimss.project.service.serviceImpl;


import com.cimss.project.database.GroupService;
import com.cimss.project.database.MemberService;
import com.cimss.project.linebot.LineMessageService;
import com.cimss.project.service.CIMSService;
import com.cimss.project.service.InstantMessagingSoftwareList;
import com.cimss.project.service.WebhookService;
import com.cimss.project.apibody.EventBean;
import com.cimss.project.database.ManagerService;
import com.cimss.project.database.UserService;
import com.cimss.project.database.entity.Group;
import com.cimss.project.database.entity.User;
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
            case LINE-> lineMessageService.pushTextMessage(userId,textMessage);
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
        return groupService.createGroup(Group.CreateGroup(groupName,groupWebhook));
    }

    @Override
    public String renameGroup(String groupId,String groupName) {
        Group group = groupService.getGroupById(groupId);
        if(group==null) return null;
        group.setGroupName(groupName);
        return groupService.renameGroup(group);
    }

    @Override
    public List<Group.GroupData> searchGroup(String groupName) {
        return groupService.getGroupByName(groupName);
    }

    @Override
    public void IMSWebhookTextEventHandler(String instantMessagingSoftware, String instantMessagingSoftwareUserId, String text) {
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
        switch (commandType) {
            case "search" -> {
                result = String.format("Search for key word \"%s\":", command.split(" ", 3)[2]);
                for (Group.GroupData group : searchGroup(command.split(" ", 3)[2])) {
                    result = String.format("%s\n\ngroup name:\n%s\ngroup id:\n%s", result, group.getGroupName(), group.getGroupId());
                }
            }
            case "groups" -> {
                result = "The groups you joined:";
                for (Group group : memberService.getGroups(instantMessagingSoftware, instantMessagingSoftwareUserId)) {
                    result = String.format("%s\n\ngroup name:\n%s\ngroup id:\n%s", result, group.getGroupName(), group.getGroupId());
                }
            }
            case "join" ->
                    result = join(instantMessagingSoftware, instantMessagingSoftwareUserId, command.split(" ", 3)[2]);
            case "leave" ->
                    result = leave(instantMessagingSoftware, instantMessagingSoftwareUserId, command.split(" ", 3)[2]);
            default -> result = "Command no found";
        }
        return result;
    }

}
