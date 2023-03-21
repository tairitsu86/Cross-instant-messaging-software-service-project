package com.my.im.study.database.serviceImpl;

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
		return optionalGroup.get();
	}

	@Override
	public List<Group> getAllGroups() {
		return groupRepository.findAll();
	}

	@Override
	public Group updateGroup(Group group) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteGroup(String groupId) {
		groupRepository.deleteById(groupId);
	}

}
