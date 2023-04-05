package com.my.im.study.database.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import com.my.im.study.database.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.my.im.study.database.GroupService;
import com.my.im.study.database.MemberService;
import com.my.im.study.database.UserService;
import com.my.im.study.database.repository.MemberRepository;

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
	public List<Group> getGroups(String instantMessagingSoftware,String instantMessagingSoftwareUserId) {
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
		return memberRepository.existsById(new ManagerId(instantMessagingSoftware,instantMessagingSoftwareUserId,groupId));
	}

}
