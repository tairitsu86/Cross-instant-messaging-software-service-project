package com.cimss.project.database.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import com.cimss.project.database.GroupService;
import com.cimss.project.database.MemberService;
import com.cimss.project.database.UserService;
import com.cimss.project.database.entity.*;
import com.cimss.project.database.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServicImpl implements MemberService {
	
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private GroupService groupService;
	
	@Override
	public void join(String instantMessagingSoftware,String instantMessagingSoftwareUserId, String groupId) {
		memberRepository.save(new Member(instantMessagingSoftware,instantMessagingSoftwareUserId,groupId));
	}

	@Override
	public String joinWithProperty(String instantMessagingSoftware, String instantMessagingSoftwareUserId, String groupId) {
		Group group = groupService.getGroupById(groupId);
		if(group==null||!group.isJoinById()) return "Group id not exist!";
		try {
			memberRepository.save(new Member(instantMessagingSoftware,instantMessagingSoftwareUserId,groupId));
		}catch (Exception e){
			return String.format("Error with %s",e.getMessage());
		}
		return "Success";
	}

	@Override
	public void leave(String instantMessagingSoftware, String instantMessagingSoftwareUserId, String groupId) {
		memberRepository.delete(new Member(instantMessagingSoftware,instantMessagingSoftwareUserId,groupId));
	}

	@Override
	public List<User> getUsers(String groupId) {
		List<Member> members = memberRepository.findAll();
		List<User> users = new ArrayList<User>();
		for(Member member: members) 
			if(member.getGroupIdForeignKey().equals(groupId))
				users.add(userService.getUserById(member.getInstantMessagingSoftwareForeignKey(),member.getInstantMessagingSoftwareUserIdForeignKey()));
		return users;
	}

	@Override
	public List<Group> getGroups(String instantMessagingSoftware, String instantMessagingSoftwareUserId) {
		List<Member> members = memberRepository.findAll();
		List<Group> groups = new ArrayList<Group>();
		UserId userId = new UserId(instantMessagingSoftware, instantMessagingSoftwareUserId);
		for(Member member: members) 
			if(userService.checkMember(member,userId))
				groups.add(groupService.getGroupById(member.getGroupIdForeignKey()));
		return groups;
	}

	@Override
	public List<Member> getAllMembers() {
		return memberRepository.findAll();
	}

	@Override
	public void deleteAllMembers() {
		memberRepository.deleteAll();
	}

	@Override
	public boolean isMember(String instantMessagingSoftware, String instantMessagingSoftwareUserId, String groupId) {
		return memberRepository.existsById(new MemberId(instantMessagingSoftware,instantMessagingSoftwareUserId,groupId));
	}

}
