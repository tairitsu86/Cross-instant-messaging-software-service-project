package com.cimss.project.database.serviceImpl;

import com.cimss.project.database.GroupService;
import com.cimss.project.database.UserService;
import com.cimss.project.database.ManagerService;
import com.cimss.project.database.entity.Manager;
import com.cimss.project.database.entity.ManagerId;
import com.cimss.project.database.repository.ManagerRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    private ManagerRespository managerRespository;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;

    @Override
    public void grantPermission(String instantMessagingSoftware,String instantMessagingSoftwareUserId, String groupId) {
        managerRespository.save(new Manager(instantMessagingSoftware,instantMessagingSoftwareUserId,groupId));
    }

    @Override
    public void revokePermission(String instantMessagingSoftware, String instantMessagingSoftwareUserId, String groupId) {
        managerRespository.delete(new Manager(instantMessagingSoftware,instantMessagingSoftwareUserId,groupId));
    }

    @Override
    public boolean isManager(String instantMessagingSoftware, String instantMessagingSoftwareUserId, String groupId) {
        return managerRespository.existsById(new ManagerId(instantMessagingSoftware,instantMessagingSoftwareUserId,groupId));
    }

    @Override
    public List<Manager> getAllManagers() {
        return managerRespository.findAll();
    }

    @Override
    public void deleteAllManagers() {
        managerRespository.deleteAll();
    }
}