package com.cimss.project.service.serviceImpl;

import com.cimss.project.apibody.EventBean;
import com.cimss.project.database.GroupService;
import com.cimss.project.database.MemberService;
import com.cimss.project.database.entity.Group;
import com.cimss.project.database.entity.User;
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
    private MemberService memberService;
    @Autowired
    private CIMSService cimsService;

    @Override
    public void TextEventHandler(String instantMessagingSoftware, String instantMessagingSoftwareUserId, String text) {
        if(text.startsWith("/cimss")){
            String executeResult = CIMSSdecoder(instantMessagingSoftware, instantMessagingSoftwareUserId,text);
            cimsService.sendTextMessage(instantMessagingSoftware,instantMessagingSoftwareUserId,executeResult);
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
            case "newgroup" ->{
                String groupName = command.split(" ", 3)[2];
                Group newGroup;
                try {
                    newGroup = cimsService.newGroup(Group.CreatePrivateGroup(groupName));
                    cimsService.join(instantMessagingSoftware,instantMessagingSoftwareUserId,newGroup.getGroupId());
                    cimsService.grantPermission(instantMessagingSoftware,instantMessagingSoftwareUserId,newGroup.getGroupId());
                }catch (Exception e){
                    result = String.format("Create Error with %s",e.getMessage());
                    break;
                }
                result = String.format("Create success,this is your group id:\n%s",newGroup.getGroupId());
            }
            case "join" -> result = memberService.joinWithProperty(instantMessagingSoftware, instantMessagingSoftwareUserId, command.split(" ", 3)[2]);
            case "leave" -> result = cimsService.leave(instantMessagingSoftware, instantMessagingSoftwareUserId, command.split(" ", 3)[2]);
            case "groups" -> {
                List<Group> joinedGroup = memberService.getGroups(instantMessagingSoftware, instantMessagingSoftwareUserId);
                if(joinedGroup.size()==0){
                    result = "Didn't join any group now!";
                    break;
                }
                result = "The groups you joined:";
                for (Group group : joinedGroup) {
                    result = String.format("%s\n\nGroup:%s,id:%s", result, group.getGroupName(), group.getGroupId());
                }
            }
            case "detail"->{
                Group.GroupDetail detail = cimsService.groupDetail(command.split(" ",3)[2]);
                result = String.format("Group:%s\nDescription:\n%s\nisPublic: %s, joinById: %s\nallMessageBroadcast: %s",detail.getGroupName(),detail.getGroupDescription(),detail.getIsPublic(),detail.getJoinById(),detail.getAllMessageBroadcast());
            }
            default ->{
                result = "Command error! Or you don't have the permission!";
                if(CommandAuthorization(instantMessagingSoftware,instantMessagingSoftwareUserId,command)){
                    //switch case for require manager permission command
                    String groupId = command.split(" ")[2];
                    switch (commandType){
                        case "members"->{
                            List<User> users = cimsService.getMembers(groupId);
                            result = String.format("Members in \"%s\":",cimsService.groupDetail(groupId).getGroupName());
                            for(User user:users){
                                result = String.format("%s\n%s %s\n%s",result,user.getInstantMessagingSoftware(),user.getUserName(),user.getInstantMessagingSoftwareUserId());
                            }
                        }
                        case "broadcast"-> result = cimsService.broadcast(groupId,command.split(" ",4)[3]);
                        case "remove"-> result = cimsService.leave(command.split(" ",5)[3],command.split(" ",5)[4],groupId);
                        case "alter"-> result = cimsService.alterGroup(groupId,command.split(" ",5)[3],command.split(" ",5)[4]);
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
