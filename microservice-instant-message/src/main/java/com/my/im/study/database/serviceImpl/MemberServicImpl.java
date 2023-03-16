package com.my.im.study.database.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.im.study.database.GroupService;
import com.my.im.study.database.MemberService;
import com.my.im.study.database.UserService;
import com.my.im.study.database.entity.Group;
import com.my.im.study.database.entity.Member;
import com.my.im.study.database.entity.User;
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
	public void join(String userId, String groupId) {
		memberRepository.save(new Member(null,userId,groupId));
	}

	@Override
	public List<User> getUsers(String groupId) {
		List<Member> members = memberRepository.findAll();
		List<User> users = new ArrayList<User>();
		for(Member member: members) 
			if(member.getGroupIdForeignKey().equals(groupId))
				users.add(userService.getUserById(member.getUserIdForeignKey()));
		return users;
	}

	@Override
	public List<Group> getGroups(String userId) {
		List<Member> members = memberRepository.findAll();
		List<Group> groups = new ArrayList<Group>();
		for(Member member: members) 
			if(member.getUserIdForeignKey().equals(userId))
				groups.add(groupService.getGroupById(member.getGroupIdForeignKey()));
		return groups;
	}

}
