package com.cimss.project.service.serviceImpl;

import com.cimss.project.apibody.EventBean;
import com.cimss.project.database.entity.Group;
import com.cimss.project.database.entity.Member;
import com.cimss.project.database.entity.User;
import com.cimss.project.database.entity.UserId;
import com.cimss.project.service.AuthorizationService;
import com.cimss.project.service.CIMSService;
import com.cimss.project.service.EventHandleService;
import com.cimss.project.service.WebhookService;
import com.cimss.project.service.token.PermissionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventHandleServiceImpl implements EventHandleService {

    @Autowired
    private WebhookService webhookService;
    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private CIMSService cimsService;

    @Override
    public void TextEventHandler(UserId userId, String text) {
        if(text.startsWith("/cimss")){
            String executeResult = CIMSSdecoder(userId,text);
            cimsService.sendTextMessage(userId,executeResult);
            return;
        }
        webhookService.webhookSendEvent(userId, EventBean.createTextMessageEventBean(cimsService.getUserById(userId) ,null,text));
    }
    public String CIMSSdecoder(UserId userId,String command){
        String commandType = command.split(" ")[1];
        String result;
        //switch case for no require permission command
        switch (commandType) {
            case "search" -> {
                String keyword = command.split(" ", 3)[2];
                List<Group.GroupData> searchResult = cimsService.searchGroup(keyword);
                if(searchResult.size()==0) {
                    result = String.format("No found with key word \"%s\"!", keyword);
                    break;
                }
                result = String.format("Search for key word \"%s\":", keyword);
                for (Group.GroupData group : searchResult) {
                    result = String.format("%s\n\nGroup:%s,id:%s\nIntroduce:\n%s", result, group.getGroupName(), group.getGroupId(), group.getGroupDescription());
                }
            }
            case "new" ->{
                String groupName = command.split(" ", 3)[2];
                Group newGroup;
                try {
                    newGroup = cimsService.newGroup(Group.CreatePrivateGroup(groupName));
                    cimsService.join(userId,newGroup.getGroupId());
                    cimsService.grantPermission(userId,newGroup.getGroupId());
                }catch (Exception e){
                    result = String.format("Create Error with %s",e.getMessage());
                    break;
                }
                result = String.format("Create success,this is your group id:\n%s",newGroup.getGroupId());
            }
            case "join" -> result = cimsService.joinWithProperty(userId, command.split(" ", 3)[2]);
            case "leave" -> result = cimsService.leave(userId, command.split(" ", 3)[2]);
            case "groups" -> {
                List<Group> joinedGroup = cimsService.getGroups(userId);
                if(joinedGroup.size()==0){
                    result = "Didn't join any group now!";
                    break;
                }
                result = "The groups you joined:";
                for (Group group : joinedGroup) {
                    result = String.format("%s\n\nGroup:%s,id:%s", result, group.getGroupName(), group.getGroupId());
                }
            }
            default ->{
                result = "Command error!\nOr you don't have the permission!";
                if(CommandAuthorization(userId,command)){
                    //switch case for require manager permission command
                    String groupId = command.split(" ")[2];
                    switch (commandType){
                        case "members"->{
                            List<Member.MemberData> members = cimsService.getMembers(groupId);
                            result = String.format("Members in \"%s\":",cimsService.groupDetail(groupId).getGroupName());
                            for(Member.MemberData member:members){
                                result = String.format("%s\n%s %s\n%s\n%s\n",result,member.getUserName(),member.getIsManager()?"Group Manager":"Normal Member",member.getUserId().getInstantMessagingSoftware(),member.getUserId().getInstantMessagingSoftwareUserId());
                            }
                        }
                        case "detail"->{
                            Group group = cimsService.groupDetail(groupId);
                            result = String.format("Group:%s\nID:%s\nDescription:\n%s\n\nAuthorizationKey: %s\nKeyword: %s\nWebhook: %s\nisPublic: %s\njoinById: %s",group.getGroupName(),group.getGroupId(),group.getGroupDescription(),group.getAuthorizationKey(),group.getGroupKeyword(),group.getGroupWebhook(),group.getIsPublic(),group.getJoinById());
                        }
                        case "broadcast"-> result = cimsService.broadcast(groupId,command.split(" ",4)[3]);
                        case "remove"-> result = cimsService.leave(UserId.CreateUserId(command.split(" ",5)[3],command.split(" ",5)[4]),groupId);
                        case "alter"-> result = cimsService.alterGroup(groupId,command.split(" ",5)[3],command.split(" ",5)[4]);
                    }
                }
            }
        }
        return result;
    }
    public boolean CommandAuthorization(UserId userId,String command){
        String cmd[] = command.split(" ");
        if(cmd.length<3) return false;
        PermissionList returnToken = authorizationService.authorization(userId,command.split(" ")[2]);
        System.err.println(returnToken);
        return returnToken.managerPermission();
    }
}
