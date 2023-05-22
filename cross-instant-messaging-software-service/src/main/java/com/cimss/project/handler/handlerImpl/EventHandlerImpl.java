package com.cimss.project.handler.handlerImpl;

import com.cimss.project.apibody.EventBean;
import com.cimss.project.database.entity.Group;
import com.cimss.project.database.entity.Member;
import com.cimss.project.database.entity.UserId;
import com.cimss.project.handler.WaitingEventHandler;
import com.cimss.project.im.ButtonList;
import com.cimss.project.security.JwtUtilities;
import com.cimss.project.service.CIMSService;
import com.cimss.project.service.CreateButtonListService;
import com.cimss.project.handler.EventHandler;
import com.cimss.project.service.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class EventHandlerImpl implements EventHandler {

    @Autowired
    private NotifyService notifyService;
    @Autowired
    private CIMSService cimsService;
    @Autowired
    private JwtUtilities jwtUtilities;
    @Autowired
    private CreateButtonListService createButtonListService;
    @Autowired
    private WaitingEventHandler waitingEventHandler;

    @Override
    public void textEventHandler(UserId userId, String text) {
        if(!cimsService.isUserExist(userId)){
            waitingEventHandler.addWaitingUser(
                    userId,
                    WaitingEventHandler.WaitingType.INIT_USERNAME,
                    text
            );
            cimsService.sendTextMessage(userId,"Hello!\nWelcome to Cross-IM Service!\nPlease tell me your name!");
        }else if(text.startsWith("/cimss")){
            commandEventHandler(userId, text);
        }else if(waitingEventHandler.checkWaiting(userId,text)){
        }else{
            cimsService.sendButtonListMessage(userId,createButtonListService.createCIMSSMenu());
        }
    }

    @Override
    public void commandEventHandler(UserId userId, String text) {
        decoder(userId,text);
    }

    @Override
    public void replyEventHandler(UserId userId,String groupId,EventBean.ReplyEvent replyEvent) {
        if(replyEvent==null) return;
        List<UserId> ignoreUser = new ArrayList<>();
        if(replyEvent.getBroadcastIgnoreExecutor()!=null&&!replyEvent.getBroadcastIgnoreExecutor())
            ignoreUser.add(userId);
        if(!(replyEvent.getBroadcastMessage()==null)&&!replyEvent.getBroadcastMessage().equals(""))
            cimsService.broadcast(groupId,replyEvent.getBroadcastMessage(),ignoreUser);
        if(!(replyEvent.getReportMessage()==null)&&!replyEvent.getReportMessage().equals(""))
            cimsService.sendTextMessage(userId,replyEvent.getReportMessage());
    }

    public void decoder(UserId executorUser, String stringCommand){
        Command command = EventHandler.CreateCommandObject(stringCommand);
        if (command.getCommandType()==CommandType.UNKNOWN) return;
        CommandType commandType = command.getCommandType();
        String groupId = command.getGroupId();
        UserId userId = command.getUserId();
        String parameter = command.getParameter();
        if(commandType.getRequireRole()!=null){
            if(!cimsService.getGroupRole(executorUser,groupId).permission(commandType.getRequireRole()))
                return;
        }
        String reply = null;
        ButtonList replyButtonList = null;
        switch (commandType) {
            //no permission
            case GROUPS -> {
                List<Group> joinedGroup = cimsService.getGroups(executorUser);
                if(joinedGroup.size()==0){
                    reply = "Didn't join any group now!";
                    break;
                }
                waitingEventHandler.addWaitingUser(executorUser, WaitingEventHandler.WaitingType.MENU_GROUP_ID,null);
                reply = "The groups you joined:";
                for (Group group : joinedGroup) {
                    reply = String.format("%s\n\nGroup:%s,id:%s", reply, group.getGroupName(), group.getGroupId());
                }
                reply = String.format("%s\n\n%s",reply,"Want to open the group list?\nJust enter the group id!\nOr push the Exit button!");
                replyButtonList = createButtonListService.exitMenu();
            }
            case KEY -> reply = jwtUtilities.generateToken(cimsService.getUserById(executorUser));
            case SEARCH_GROUP -> {
                waitingEventHandler.addWaitingUser(executorUser, WaitingEventHandler.WaitingType.SEARCH_KEYWORD,null);
                reply = "Please enter the search keyword!";
            }
            case NEW_GROUP ->{
                waitingEventHandler.addWaitingUser(executorUser, WaitingEventHandler.WaitingType.CREATE_GROUP_NAME,null);
                reply = "Please enter the group name!";
            }
            case PROFILE -> replyButtonList = createButtonListService.createProfileMenu(executorUser);
            case EXIT -> reply = waitingEventHandler.exitWaiting(executorUser)?"Exit success!":"Nothing to exit!";
            case ALTER_USERNAME -> {
                waitingEventHandler.addWaitingUser(executorUser, WaitingEventHandler.WaitingType.ALTER_USERNAME,null);
                reply = "Please enter your new name";
            }


            //member permission
            case NOTIFY -> replyEventHandler(
                    executorUser,
                    groupId,
                    notifyService.notify(groupId,EventBean.CreateFunctionListEvent(parameter)));
            case TO_PATH -> replyButtonList = createButtonListService.createGroupServiceMenu(groupId,parameter);
            case LEAVE -> {
                cimsService.leave(executorUser, groupId);
                reply = "Leave success!";
            }



            //manager permission
            case MEMBERS->{
                List<Member.MemberData> members = cimsService.getMembers(groupId);
                reply = String.format("Members in \"%s\":",cimsService.groupDetail(groupId).getGroupName());
                for(Member.MemberData member:members){
                    //TODO debug member.getUser().getUserDisplayName()==""
                    reply = String.format("%s\n%s %s\n%s\n%s\n",reply,member.getUser().getUserDisplayName(),member.getGroupRole(),member.getUser().getUserId().getInstantMessagingSoftware(),member.getUser().getUserId().getInstantMessagingSoftwareUserId());
                }
                replyButtonList = createButtonListService.createGroupManageAlterMemberMenu(executorUser,groupId);
            }
            case DETAIL->replyButtonList = createButtonListService.createGroupManageDetailMenu(executorUser,groupId);
            case BROADCAST-> {
                waitingEventHandler.addWaitingUser(executorUser, WaitingEventHandler.WaitingType.BROADCAST_MESSAGE,groupId);
                reply = "Please enter the message you want to broadcast!";
            }
            case REMOVE-> {
                waitingEventHandler.addWaitingUser(executorUser, WaitingEventHandler.WaitingType.REMOVE_MEMBER_IM_USER_ID,groupId);
                reply = "Please enter the IM and ID of that member,IM and ID please split by one space!";
            }
            case ALTER_GROUP_NAME -> {
                waitingEventHandler.addWaitingUser(executorUser, WaitingEventHandler.WaitingType.ALTER_GROUP_NAME,groupId);
                reply = "Please enter the new name of this group!";
            }
            case ALTER_GROUP_DESCRIPTION -> {
                waitingEventHandler.addWaitingUser(executorUser, WaitingEventHandler.WaitingType.ALTER_GROUP_DESCRIPTION,groupId);
                reply = "Please enter the new description!";
            }
            case ALTER_GROUP_IS_PUBLIC -> {
                waitingEventHandler.addWaitingUser(executorUser, WaitingEventHandler.WaitingType.ALTER_GROUP_IS_PUBLIC,groupId);
                replyButtonList = createButtonListService.createGroupManageDetailPropertyMenu(executorUser,groupId,"isPublic");
            }
            case ALTER_GROUP_JOIN_BY_ID -> {
                waitingEventHandler.addWaitingUser(executorUser, WaitingEventHandler.WaitingType.ALTER_GROUP_JOIN_BY_ID,groupId);
                replyButtonList = createButtonListService.createGroupManageDetailPropertyMenu(executorUser,groupId,"joinById");
            }
            case MANAGER_MENU -> replyButtonList = createButtonListService.createGroupManageMenu(executorUser,groupId);



            //owner permission
            case ALTER_MEMBER_ROLE_OWNER -> {
                waitingEventHandler.addWaitingUser(executorUser, WaitingEventHandler.WaitingType.TO_OWNER_IM_USER_ID,groupId);
                reply = "Please enter the IM and ID of that member,IM and ID please split by one space!";
            }
            case ALTER_MEMBER_ROLE_MANAGER -> {
                waitingEventHandler.addWaitingUser(executorUser, WaitingEventHandler.WaitingType.TO_MANAGER_IM_USER_ID,groupId);
                reply = "Please enter the IM and ID of that member,IM and ID please split by one space!";
            }
            case ALTER_MEMBER_ROLE_MEMBER -> {
                waitingEventHandler.addWaitingUser(executorUser, WaitingEventHandler.WaitingType.TO_MEMBER_IM_USER_ID,groupId);
                reply = "Please enter the IM and ID of that member,IM and ID please split by one space!";
            }
            case DELETE_GROUP -> {
                cimsService.deleteGroup(groupId);
                reply = "Delete Success";
            }
        }
        if(reply!=null)
            cimsService.sendTextMessage(executorUser,reply);
        if(replyButtonList!=null)
            cimsService.sendButtonListMessage(executorUser,replyButtonList);
    }

}
