package com.my.im.study.database;

import java.util.List;

import com.my.im.study.database.entity.Group;
import com.my.im.study.database.entity.Member;
import com.my.im.study.database.entity.User;

public interface MemberService {
	
	void join(String instantMessagingSoftware,String instantMessagingSoftwareUserId,String groupId);

	void leave(String instantMessagingSoftware,String instantMessagingSoftwareUserId,String groupId);
	
	List<User> getUsers(String groupId);
	
	List<Group> getGroups(String instantMessagingSoftware,String instantMessagingSoftwareUserId);

	List<Member> getAllMembers();

	void deleteAllMembers();

	boolean isMember(String instantMessagingSoftware,String instantMessagingSoftwareUserId,String groupId);
}
