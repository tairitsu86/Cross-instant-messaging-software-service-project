package com.cimss.project.handler.handlerImpl;

import com.cimss.project.database.DatabaseService;
import com.cimss.project.database.entity.Group;
import com.cimss.project.database.entity.UserId;
import com.cimss.project.database.entity.token.GroupRole;
import com.cimss.project.handler.WaitingEventHandler;
import com.cimss.project.im.ButtonList;
import com.cimss.project.service.CIMSService;
import com.cimss.project.service.CreateButtonListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WaitingEventHandlerImpl implements WaitingEventHandler {

    private static Map<UserId, MetaData> waitingUsers = new ConcurrentHashMap<>();

    @Autowired
    private CIMSService cimsService;

    @Autowired
    private DatabaseService databaseService;
    @Autowired
    private CreateButtonListService createButtonListService;

    @Override
    public boolean checkWaiting(UserId userId, String data) {
        MetaData metaData = waitingUsers.remove(userId);
        if(metaData==null) return false;
        waitingEventHandler(userId,metaData,data);
        return true;
    }

    @Override
    public void addWaitingUser(UserId userId,WaitingType waitingType,String tempData) {
        waitingUsers.put(userId,WaitingEventHandler.CreateMetadata(waitingType,tempData));
    }

    @Override
    public boolean exitWaiting(UserId userId) {
        return waitingUsers.remove(userId)!=null;
    }

    public void waitingEventHandler(UserId executorUser, MetaData metaData, String data){
        String reply = null;
        ButtonList replyButtonList = null;
        switch (metaData.getWaitingType()){
            case INIT_USERNAME->{
                cimsService.userRegister(executorUser,data);
                reply = String.format("%s!\nGlad you joined this service!",data);
            }
            case ALTER_USERNAME->{
                cimsService.alterUserName(executorUser,data);
                reply = String.format("Alter success!\nHello %s!",data);
            }
            case SEARCH_KEYWORD->{
                String keyword = data;
                List<Group.GroupData> searchResult = cimsService.searchGroup(keyword);
                if(searchResult.size()==0) {
                    reply = String.format("No found with key word \"%s\"!", keyword);
                }else {
                    reply = String.format("Search for key word \"%s\":", keyword);
                    for (Group.GroupData group : searchResult) {
                        reply = String.format("%s\n\nGroup:%s,id:%s\nIntroduce:\n%s", reply, group.getGroupName(), group.getGroupId(), group.getGroupDescription());
                    }
                    reply = String.format("%s\n\n%s", reply,"Want to join?\nJust enter the group id!\nOr push the Exit button!");
                    addWaitingUser(executorUser,WaitingType.JOIN_GROUP_ID,null);
                    replyButtonList = createButtonListService.exitMenu();
                }

            }
            case MENU_GROUP_ID->cimsService.sendButtonListMessage(executorUser,createButtonListService.createGroupMenu(executorUser,data));
            case JOIN_GROUP_ID->{
                cimsService.joinWithProperty(executorUser,data);
                //TODO add check
                reply = "Join success!";
            }
            case CREATE_GROUP_NAME->{
                String groupName = data;
                Group newGroup = cimsService.newGroup(Group.CreateGroup(groupName));
                cimsService.join(executorUser,newGroup.getGroupId());
                cimsService.alterPermission(executorUser,newGroup.getGroupId(), GroupRole.GROUP_OWNER);
                reply = String.format("Create success,this is your group id:\n%s",newGroup.getGroupId());
            }
            case ALTER_GROUP_NAME->{
                cimsService.alterGroupName(metaData.getTempData(), data);
                reply = "Alter success!";
            }
            case ALTER_GROUP_DESCRIPTION->{
                cimsService.alterGroupDescription(metaData.getTempData(), data);
                reply = "Alter success!";
            }
            case ALTER_GROUP_IS_PUBLIC->{
                cimsService.alterGroupIsPublic(metaData.getTempData(), Boolean.parseBoolean(data));
                reply = "Alter success!";
            }
            case ALTER_GROUP_JOIN_BY_ID->{
                cimsService.alterGroupJoinById(metaData.getTempData(), Boolean.parseBoolean(data));
                reply = "Alter success!";
            }
            case BROADCAST_MESSAGE-> cimsService.broadcast(metaData.getTempData(),data,null);
            case REMOVE_MEMBER_IM_USER_ID->{
                UserId userId = UserId.CreateUserId(data);
                //TODO add check
//                databaseService.isUserExist(userId);
                cimsService.leave(userId,metaData.getTempData());
                reply = "Alter success!";
            }
            case TO_MEMBER_IM_USER_ID->{
                UserId userId = UserId.CreateUserId(data);
                //TODO add check
//                databaseService.isUserExist(userId);
                cimsService.alterPermission(userId,metaData.getTempData(),GroupRole.GROUP_MEMBER);
                reply = "Alter success!";
            }
            case TO_MANAGER_IM_USER_ID->{
                UserId userId = UserId.CreateUserId(data);
                //TODO add check
//                databaseService.isUserExist(userId);
                cimsService.alterPermission(userId,metaData.getTempData(),GroupRole.GROUP_MANAGER);
                reply = "Alter success!";
            }
            case TO_OWNER_IM_USER_ID->{
                UserId userId = UserId.CreateUserId(data);
                //TODO add check
//                databaseService.isUserExist(userId);
                cimsService.alterPermission(userId,metaData.getTempData(),GroupRole.GROUP_OWNER);
                reply = "Alter success!";
            }

        }
        if(reply!=null)
            cimsService.sendTextMessage(executorUser,reply);
        if(replyButtonList!=null)
            cimsService.sendButtonListMessage(executorUser,replyButtonList);
    }

}
