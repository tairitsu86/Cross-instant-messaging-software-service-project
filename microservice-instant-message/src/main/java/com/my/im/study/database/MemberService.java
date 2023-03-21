package com.my.im.study.database;

import java.util.List;

import com.my.im.study.database.entity.Group;
import com.my.im.study.database.entity.User;

public interface MemberService {
	
	public void join(String instantMessagingSoftware,String instantMessagingSoftwareUserId,String groupId);
	
	public List<User> getUsers(String groupId);
	
	public List<Group> getGroups(String instantMessagingSoftware,String instantMessagingSoftwareUserId);
}
