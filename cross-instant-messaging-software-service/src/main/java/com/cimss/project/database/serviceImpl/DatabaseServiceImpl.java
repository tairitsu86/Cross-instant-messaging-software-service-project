package com.cimss.project.database.serviceImpl;

import com.cimss.project.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseServiceImpl implements DatabaseService {

    @Autowired
    private GroupService groupService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private UserService userService;

    @Override
    public void deleteAllData() {
        memberService.deleteAllMembers();
        userService.deleteAllUsers();
        groupService.deleteAllGroups();
    }
}
