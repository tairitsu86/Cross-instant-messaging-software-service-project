package com.my.im.study.database;

import java.util.List;

import com.my.im.study.database.entity.Group;

public interface GroupService {
	Group createGroup(Group group);

    String renameGroup(Group group);

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
