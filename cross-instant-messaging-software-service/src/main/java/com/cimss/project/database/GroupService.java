package com.cimss.project.database;

import java.util.List;

import com.cimss.project.database.entity.Group;

public interface GroupService {
	Group createGroup(Group group);
    String alterGroup(Group group);
	Group getGroupById(String groupId);
    List<Group.GroupData> getGroupByName(String groupName);
    String getGroupIdByAuthorizationKey(String authorizationKey);
    List<Group> getAllGroups();
    String deleteGroup(String groupId);
    void deleteAllGroups();
}
