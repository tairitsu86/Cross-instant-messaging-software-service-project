package com.cimss.project.database.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cimss.project.database.GroupService;
import com.cimss.project.database.entity.Group;
import com.cimss.project.database.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public String renameGroup(Group group) {
		try {
			groupRepository.save(group);
		}catch (Exception e){
			return e.getMessage();
		}
		return "Success!";
	}

	@Override
	public Group getGroupById(String groupId) {
		Optional<Group> optionalGroup = groupRepository.findById(groupId);
		if(optionalGroup.isEmpty()) return null;
		return optionalGroup.get();
	}

	@Override
	public List<Group.GroupData> getGroupByName(String groupName) {
		List<Group> groups = getAllGroups();
		List<Group.GroupData> result = new ArrayList<>();
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

	@Override
	public String getAuthorizationKey(String groupId) {
		Group group = getGroupById(groupId);
		if(group==null) return null;
		return group.getAuthorizationKey();
	}

	@Override
	public String getGroupByAuthorizationKey(String authorizationKey) {
		List<Group> groups = getAllGroups();
		for (Group group:groups){
			if(group.getAuthorizationKey().equals(authorizationKey))
				return group.getGroupId();
		}
		return null;
	}

}
