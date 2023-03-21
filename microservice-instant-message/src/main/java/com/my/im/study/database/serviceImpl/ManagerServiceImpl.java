package com.my.im.study.database.serviceImpl;

import com.my.im.study.database.GroupService;
import com.my.im.study.database.ManagerService;
import com.my.im.study.database.UserService;
import com.my.im.study.database.entity.Manager;
import com.my.im.study.database.entity.UserId;
import com.my.im.study.database.repository.ManagerRespository;
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
    public boolean checkPermission(String instantMessagingSoftware,String instantMessagingSoftwareUserId, String groupId) {
        if(instantMessagingSoftwareUserId.equals(System.getenv("MIM_ADMIN_TOKEN"))) return true;
        List<Manager> managers = managerRespository.findAll();
        UserId userId = new UserId(instantMessagingSoftware,instantMessagingSoftwareUserId);
        for (Manager manager : managers)
            if(userService.checkManage(manager,userId)&& manager.getGroupIdForeignKey().equals(groupId))
                return true;
        return false;
    }

    @Override
    public List<Manager> getAllManagers() {
        return managerRespository.findAll();
    }
}
