package com.my.im.study.database;

import java.util.List;

import com.my.im.study.database.entity.Group;

public interface GroupService {
	Group createGroup(Group group);

	Group getGroupById(String groupId);

    List<Group> getAllGroups();

    Group updateGroup(Group group);

    void deleteGroup(String groupId);

    void deleteAllGroups();
}
