package com.cimss.project.database.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import com.cimss.project.database.GroupService;
import com.cimss.project.database.MemberService;
import com.cimss.project.database.UserService;
import com.cimss.project.database.entity.*;
import com.cimss.project.database.entity.token.GroupRole;
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
	public String join(UserId userId, String groupId) {
		try {
			memberRepository.save(Member.CreateMember(userId,groupId));
		}catch (Exception e){
			return String.format("Error with Exception:%s",e.getMessage());
		}
		return "Success";
	}

	@Override
	public String joinWithProperty(UserId userId, String groupId) {
		Group group = groupService.getGroupById(groupId);
		if(group==null||!group.getJoinById()) return "That group not allow join by group id";
		return join(userId,groupId);
	}

	@Override
	public String leave(UserId userId, String groupId) {
		try {
			memberRepository.deleteById(MemberId.CreateMemberId(userId,groupId));
		}catch (Exception e){
			return String.format("Error with Exception:%s",e.getMessage());
		}
		return "Success";
	}

	@Override
	public String alterPermission(UserId userId, String groupId, GroupRole groupRole) {
		Member member = memberRepository.getReferenceById(MemberId.CreateMemberId(userId,groupId));
		member.setGroupRole(groupRole);
		memberRepository.save(member);
		return "Success";
	}

	@Override
	public List<User> getUsers(String groupId) {
		List<Member> members = memberRepository.findAll();
		List<User> users = new ArrayList<User>();
		for(Member member: members)
			if(member.getMemberId().getGroup().getGroupId().equals(groupId))
				users.add(member.getMemberId().getUser());
		return users;
	}
	@Override
	public List<Member.MemberData> getMembers(String groupId) {
		List<Member> members = memberRepository.findAll();
		List<Member.MemberData> memberData = new ArrayList<>();
		for(Member member: members)
			if(member.getMemberId().getGroup().getGroupId().equals(groupId))
				memberData.add(member.toMemberData());
		return memberData;
	}

	@Override
	public List<Group> getGroups(UserId userId) {
		List<Member> members = memberRepository.findAll();
		List<Group> groups = new ArrayList<>();
		for(Member member: members)
			if(member.getMemberId().getUser().getUserId().equals(userId))
				groups.add(member.getMemberId().getGroup());
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
	public boolean isMember(UserId userId, String groupId) {
		return memberRepository.existsById(MemberId.CreateMemberId(userId,groupId));
	}

	@Override
	public Member getMemberById(MemberId memberId) {
		//TODO
		return memberRepository.getReferenceById(memberId);
	}

	@Override
	public List<String> getRoles(UserId userId) {
		List<Member> members = memberRepository.findAll();
		List<String> roles = new ArrayList<>();
		for(Member member: members)
			if(member.getMemberId().getUser().getUserId().equals(userId))
				roles.add(String.format("%s %s",member.getMemberId().getGroup().getGroupId(),member.getGroupRole().name()));
		return roles;
	}


}
