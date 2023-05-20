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
		group.setGroupId(newId());
		return groupRepository.save(group);
	}
	public String newId(){
		char newId[] = new char[6];
		for(int i=0,random;i<6;i++){
			random = (int)(Math.random()*62);
			if(random<10)
				newId[i] = (char)(random+48);
			else if (random<36)
				newId[i] = (char)(random-10+65);
			else
				newId[i] = (char)(random-36+97);
		}
		if(groupRepository.existsById(String.copyValueOf(newId))) return newId();
		return String.copyValueOf(newId);
	}

	@Override
	public void alterGroup(Group newGroup) {
		Group oldGroup = getGroupById(newGroup.getGroupId());
		if(oldGroup==null) return;
		oldGroup.copyFromObject(newGroup);
		if(oldGroup==null) return;
		groupRepository.save(oldGroup);
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
		if(groupName==null) groupName = "";
		for(Group group:groups){
			if(group.getGroupName()!=null&&group.getIsPublic()&&group.getGroupName().matches("(?i).*"+groupName+".*")){
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


}
