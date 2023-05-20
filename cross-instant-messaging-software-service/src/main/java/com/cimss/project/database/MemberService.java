package com.cimss.project.database;

import java.util.List;

import com.cimss.project.database.entity.*;
import com.cimss.project.database.entity.token.GroupRole;

public interface MemberService {
	void join(UserId userId,String groupId);
	void joinWithProperty(UserId userId,String groupId);
	void leave(UserId userId,String groupId);
	void alterPermission(UserId userId, String groupId, GroupRole groupRole);
	List<User> getUsers(String groupId);
	List<Member.MemberData> getMembers(String groupId);
	List<Group> getGroups(UserId userId);
	List<Member> getAllMembers();
	void deleteAllMembers();
	boolean isMember(UserId userId, String groupId);
	Member getMemberById(MemberId memberId);
	List<String> getRoles(UserId userId);
}
