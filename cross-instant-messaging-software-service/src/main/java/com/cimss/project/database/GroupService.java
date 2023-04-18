package com.cimss.project.database;

import java.util.List;

import com.cimss.project.database.entity.Group;

public interface GroupService {
	Group createGroup(Group group);

    String alterGroup(Group group);

	Group getGroupById(String groupId);

    List<Group.GroupData> getGroupByName(String groupName);

    List<Group> getAllGroups();

    void deleteGroup(String groupId);

    void deleteAllGroups();

    String setWebhook(String groupId,String webhook);

    String getWebhook(String groupId);
    String getAuthorizationKey(String groupId);

    String getGroupByAuthorizationKey(String authorizationKey);
}
