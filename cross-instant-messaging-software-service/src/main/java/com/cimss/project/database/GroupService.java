package com.cimss.project.database;

import java.util.List;

import com.cimss.project.database.entity.Group;

public interface GroupService {
	Group createGroup(Group group);
    void alterGroup(Group group);
	Group getGroupById(String groupId);
    List<Group.GroupData> getGroupByName(String groupName);
    List<Group> getAllGroups();
    void deleteGroup(String groupId);
    void deleteAllGroups();
}
