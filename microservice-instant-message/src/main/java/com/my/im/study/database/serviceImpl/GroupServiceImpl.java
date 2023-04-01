package com.my.im.study.database.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.im.study.database.GroupService;
import com.my.im.study.database.entity.Group;
import com.my.im.study.database.repository.GroupRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GroupServiceImpl implements GroupService {
	
	@Autowired
    private GroupRepository groupRepository;
	
	@Override
	public Group createGroup(Group group) {
		return groupRepository.save(group);
	}

	@Override
	public Group getGroupById(String groupId) {
		Optional<Group> optionalGroup = groupRepository.findById(groupId);
		if(optionalGroup.isEmpty()) return null;
		return optionalGroup.get();
	}

	@Override
	public List<Group> getGroupByName(String groupName) {
		List<Group> groups = getAllGroups();
		List<Group> result = new ArrayList<>();
		for(Group group:groups){
			if(groupName!=null&&group.getGroupName()!=null&&group.getGroupName().matches("(?i).*"+groupName+".*")){
				result.add(Group.CreateDataBean(group));
			}
		}
		return result;
	}

	@Override
	public List<Group> getAllGroups() {
		return groupRepository.findAll();
	}

	@Override
	public void deleteGroup(String groupId) {
		groupRepository.deleteById(groupId);
	}

	@Override
	public void deleteAllGroups() {
		groupRepository.deleteAll();
	}

	@Override
	public String setWebhook(String groupId,String webhook) {
		Group group = getGroupById(groupId);
		if(group==null) return "Group not exist!";
		group.setGroupWebhook(webhook);
		groupRepository.save(group);
		return "Success set webhook:"+group.getGroupWebhook();
	}

	@Override
	public String getWebhook(String groupId) {
		Group group = getGroupById(groupId);
		if(group==null) return "Group not exist!";
		return group.getGroupWebhook();
	}

}
