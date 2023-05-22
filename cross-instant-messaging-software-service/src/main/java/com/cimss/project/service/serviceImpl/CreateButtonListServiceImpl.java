package com.cimss.project.service.serviceImpl;

import com.cimss.project.database.DatabaseService;
import com.cimss.project.database.entity.Group;
import com.cimss.project.database.entity.UserId;
import com.cimss.project.handler.EventHandler;
import com.cimss.project.im.ButtonList;
import com.cimss.project.service.CreateButtonListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class CreateButtonListServiceImpl implements CreateButtonListService {
    @Autowired
    private DatabaseService databaseService;

    @Override
    public ButtonList createCIMSSMenu() {
        Map<String,String> buttons = new HashMap<>();
        buttons.put("My profile",
                EventHandler.CommandBuilder(EventHandler.CommandType.PROFILE)
                        .build().toString()
        );
        buttons.put("My groups"
                ,EventHandler.CommandBuilder(EventHandler.CommandType.GROUPS)
                        .build().toString()
        );
        buttons.put("Search group"
                ,EventHandler.CommandBuilder(EventHandler.CommandType.SEARCH_GROUP)
                        .build().toString()
        );
        buttons.put("Create group"
                ,EventHandler.CommandBuilder(EventHandler.CommandType.NEW_GROUP)
                        .build().toString()
        );
        return ButtonList.CreateButtonList(
                "Cross-IM Service",
                "Hello!\nNeed any help?",
                buttons);
    }

    @Override
    public ButtonList exitMenu() {
        Map<String,String> buttons = new HashMap<>();
        buttons.put("Exit"
                ,EventHandler.CommandBuilder(EventHandler.CommandType.EXIT)
                        .build().toString()
        );
        return ButtonList.CreateButtonList(
                "Exit",
                "Just Exit button.",
                buttons);
    }

    @Override
    public ButtonList createProfileMenu(UserId userId) {
        Map<String,String> buttons = new HashMap<>();
        buttons.put("Alter username",
                EventHandler.CommandBuilder(EventHandler.CommandType.ALTER_USERNAME)
                        .build().toString()
        );
        buttons.put("Get API key",
                EventHandler.CommandBuilder(EventHandler.CommandType.KEY)
                        .build().toString()
        );
        return ButtonList.CreateButtonList(
                "Profile",
                 String.format("Hello %s!",databaseService.getUserById(userId).getUserDisplayName()),
                buttons);
    }


    @Override
    public ButtonList createGroupMenu(UserId userId, String groupId) {
        //member
        Map<String,String> buttons = new HashMap<>();
        buttons.put("Group Service",
                EventHandler.CommandBuilder(EventHandler.CommandType.TO_PATH)
                        .setGroupId(groupId)
                        .setParameter("/")
                        .build().toString()
        );
        buttons.put("Leave",
                EventHandler.CommandBuilder(EventHandler.CommandType.REMOVE)
                        .setGroupId(groupId)
                        .build().toString()
        );
        //manager
        buttons.put("Manage",
                EventHandler.CommandBuilder(EventHandler.CommandType.MANAGER_MENU)
                        .setGroupId(groupId)
                        .build().toString()
        );
        return ButtonList.CreateButtonList(
                databaseService.getGroupById(groupId).getGroupName(),
                databaseService.getGroupById(groupId).getGroupDescription(),
                buttons);
    }

    @Override
    public ButtonList createGroupServiceMenu(String groupId, String path) {
        //member
        return databaseService.getGroupById(groupId).getFunctionList().path(path).toButtonList(groupId);
    }

    @Override
    public ButtonList createGroupManageMenu(UserId userId, String groupId) {
        //manager
        Map<String,String> buttons = new HashMap<>();
        buttons.put("Group members",
                EventHandler.CommandBuilder(EventHandler.CommandType.MEMBERS)
                        .setGroupId(groupId)
                        .build().toString()
        );
        buttons.put("Group Detail",
                EventHandler.CommandBuilder(EventHandler.CommandType.DETAIL)
                        .setGroupId(groupId)
                        .build().toString()
        );
        buttons.put("Broadcast",
                EventHandler.CommandBuilder(EventHandler.CommandType.BROADCAST)
                        .setGroupId(groupId)
                        .build().toString()
        );
        //owner
        buttons.put("Delete group",
                EventHandler.CommandBuilder(EventHandler.CommandType.DELETE_GROUP)
                        .setGroupId(groupId)
                        .build().toString()
        );
        return ButtonList.CreateButtonList(
                "Manage",
                String.format("Manage %s group!",databaseService.getGroupById(groupId).getGroupName()),
                buttons);
    }

    @Override
    public ButtonList createGroupManageDetailMenu(UserId userId, String groupId) {
        //manager
        Map<String,String> buttons = new HashMap<>();
        buttons.put("Alter group name",
                EventHandler.CommandBuilder(EventHandler.CommandType.ALTER_GROUP_NAME)
                        .setGroupId(groupId)
                        .build().toString()
        );
        buttons.put("Alter description",
                EventHandler.CommandBuilder(EventHandler.CommandType.ALTER_GROUP_DESCRIPTION)
                        .setGroupId(groupId)
                        .build().toString()
        );
        buttons.put("Alter isPublic",
                EventHandler.CommandBuilder(EventHandler.CommandType.ALTER_GROUP_IS_PUBLIC)
                        .setGroupId(groupId)
                        .build().toString()
        );
        buttons.put("Alter joinById",
                EventHandler.CommandBuilder(EventHandler.CommandType.ALTER_GROUP_JOIN_BY_ID)
                        .setGroupId(groupId)
                        .build().toString()
        );
        Group group = databaseService.getGroupById(groupId);
        return ButtonList.CreateButtonList(
                "Detail",
                String.format("ID:%s\nName:%s\nisPublish:%s\njoinById:%s",
                        group.getGroupId(),
                        group.getGroupName(),
                        group.getIsPublic(),
                        group.getJoinById()),
                buttons);
    }

    @Override
    public ButtonList createGroupManageDetailPropertyMenu(UserId userId, String groupId,String property) {
        //manager
        Map<String,String> buttons = new HashMap<>();
        buttons.put("TRUE", "true");
        buttons.put("FALSE","false");
        Group group = databaseService.getGroupById(groupId);
        return ButtonList.CreateButtonList(
                String.format("%s",property),
                String.format("Alter %s!",property),
                buttons);
    }

    @Override
    public ButtonList createGroupManageAlterMemberMenu(UserId userId, String groupId) {
        //manager
        Map<String,String> buttons = new HashMap<>();
        buttons.put("Remove",
                EventHandler.CommandBuilder(EventHandler.CommandType.REMOVE)
                        .setGroupId(groupId)
                        .build().toString()
        );
        //owner
        buttons.put("Owner",
                EventHandler.CommandBuilder(EventHandler.CommandType.ALTER_MEMBER_ROLE_OWNER)
                        .setGroupId(groupId)
                        .build().toString()
        );
        buttons.put("Manager",
                EventHandler.CommandBuilder(EventHandler.CommandType.ALTER_MEMBER_ROLE_MANAGER)
                        .setGroupId(groupId)
                        .build().toString()
        );
        buttons.put("Member",
                EventHandler.CommandBuilder(EventHandler.CommandType.ALTER_MEMBER_ROLE_MEMBER)
                        .setGroupId(groupId)
                        .build().toString()
        );
        return ButtonList.CreateButtonList(
                "Alter member role",
                "What role do you want to give this member?",
                buttons);
    }
}
