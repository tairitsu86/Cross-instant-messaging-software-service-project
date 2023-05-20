package com.cimss.project.service.serviceImpl;

import com.cimss.project.apibody.EventBean;
import com.cimss.project.database.entity.Group;
import com.cimss.project.database.entity.Member;
import com.cimss.project.database.entity.UserId;
import com.cimss.project.database.entity.token.InstantMessagingSoftware;
import com.cimss.project.security.JwtUtilities;
import com.cimss.project.service.CIMSService;
import com.cimss.project.service.EventHandleService;
import com.cimss.project.service.NotifyService;
import com.cimss.project.database.entity.token.GroupRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class EventHandleServiceImpl implements EventHandleService {

    @Autowired
    private NotifyService notifyService;
    @Autowired
    private CIMSService cimsService;

    @Autowired
    private JwtUtilities jwtUtilities;

    @Override
    public void TextEventHandler(UserId userId, String text) {
        if(text.startsWith("/cimss")){
            String executeResult = CIMSSdecoder(userId,text);
            cimsService.sendTextMessage(userId,executeResult);
            return;
        }
        //TODO
        notifyService.webhookSendEvent(userId, EventBean.createTextMessageEventBean(cimsService.getUserById(userId) ,null,text));
    }
//    /cimss <command type> <group id> <software> <userId> <var>
    public String CIMSSdecoder(UserId userId,String stringCommand){
        Command command = CreateCommandObject(stringCommand);
        String result = "no result";
        //switch case for no require permission command
        switch (command.commandType) {
            case "test" ->cimsService.sendButtonListMessage(userId,cimsService.getGroupById(command.groupId).getFunctionList().toButtonList(command.getGroupId()));
            case "notify" -> result = "Notify!";
            case "toPath" -> cimsService.sendButtonListMessage(
                    userId,
                    cimsService
                            .getGroupById(command.groupId)
                            .getFunctionList()
                            .path(command.parameter)
                            .toButtonList(command.groupId));
            case "search" -> {
                String keyword = command.parameter;
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
                String groupName = command.parameter;
                Group newGroup;
                try {
                    newGroup = cimsService.newGroup(Group.CreateGroup(groupName));
                    cimsService.join(userId,newGroup.getGroupId());
                    cimsService.alterPermission(userId,newGroup.getGroupId(), GroupRole.GROUP_OWNER);
                }catch (Exception e){
                    result = String.format("Create Error with %s",e.getMessage());
                    break;
                }
                result = String.format("Create success,this is your group id:\n%s",newGroup.getGroupId());
            }
            case "join" -> cimsService.joinWithProperty(userId, command.groupId);
            case "leave" -> cimsService.leave(userId, command.groupId);
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
            case "key" -> result = jwtUtilities.generateToken(cimsService.getUserById(userId));
            case "members"->{
                List<Member.MemberData> members = cimsService.getMembers(command.groupId);
                result = String.format("Members in \"%s\":",cimsService.groupDetail(command.groupId).getGroupName());
                for(Member.MemberData member:members){
                    result = String.format("%s\n%s %s\n%s\n%s\n",result,member.getUser().getUserName(),member.getGroupRole(),member.getUser().getUserId().getInstantMessagingSoftware(),member.getUser().getUserId().getInstantMessagingSoftwareUserId());
                }
            }
            case "detail"->cimsService.groupDetail(command.groupId).toString();
            case "broadcast"-> cimsService.broadcast(command.groupId,command.parameter,null);
            case "remove"-> cimsService.leave(command.userId,command.groupId);
//            case "alter"-> cimsService.alterGroup(command.groupId,command.split(" ",5)[3],command.split(" ",5)[4]);
            default -> result = "Command error!\nOr you don't have the permission!";
        }
        return result;
    }
    public boolean CommandAuthorization(UserId userId,String command){
        String cmd[] = command.split(" ");
        if(cmd.length<3) return false;
//        Role returnToken = authorizationService.authorization(userId,command.split(" ")[2]);
//        System.err.println(returnToken);
        //TODO
        return true;
    }
    public Command CreateCommandObject(String command){
        String parameters[] = Arrays.copyOf(command.split(" ", 6),6);
        Command c = new Command(parameters[1],parameters[2], null, parameters[5]);
        try {
            c.userId = UserId.CreateUserId(InstantMessagingSoftware.getInstantMessagingSoftwareToken(parameters[3]),parameters[4]);
        }catch (Exception e){
        }
        return c;
    }
    @Getter
    @AllArgsConstructor
    class Command{
        private String commandType;
        private String groupId;
        private UserId userId;
        private String parameter;

    }
}
