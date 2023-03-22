package com.my.im.study.database.serviceImpl;

import com.my.im.study.database.*;
import org.springframework.beans.factory.annotation.Autowired;

public class DatabaseServiceImpl implements DatabaseService {

    @Autowired
    private GroupService groupService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private UserService userService;

    @Override
    public void deleteAllData() {
        groupService.deleteAllGroups();
        managerService.deleteAllManagers();
        memberService.deleteAllMembers();
        userService.getAllUsers();
    }
}
