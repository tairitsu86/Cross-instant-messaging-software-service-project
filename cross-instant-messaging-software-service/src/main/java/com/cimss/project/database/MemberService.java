package com.cimss.project.database;

import java.util.List;

import com.cimss.project.database.entity.Group;
import com.cimss.project.database.entity.Member;
import com.cimss.project.database.entity.User;
import com.cimss.project.database.entity.UserId;

public interface MemberService {

	String join(UserId userId,String groupId);

	String joinWithProperty(UserId userId,String groupId);

	String leave(UserId userId,String groupId);

	String grantPermission(UserId userId,String groupId);

	String revokePermission(UserId userId,String groupId);
	
	List<User> getUsers(String groupId);
	
	List<Group> getGroups(UserId userId);

	List<Member> getAllMembers();

	void deleteAllMembers();

	boolean isMember(UserId userId, String groupId);
	boolean isManager(UserId userId,String groupId);
}
