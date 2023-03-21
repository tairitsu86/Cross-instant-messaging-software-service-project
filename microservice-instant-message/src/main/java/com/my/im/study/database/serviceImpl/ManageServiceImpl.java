package com.my.im.study.database.serviceImpl;

import com.my.im.study.database.GroupService;
import com.my.im.study.database.ManageService;
import com.my.im.study.database.UserService;
import com.my.im.study.database.entity.Manage;
import com.my.im.study.database.entity.UserId;
import com.my.im.study.database.repository.ManageRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManageServiceImpl implements ManageService {
    @Autowired
    private ManageRespository manageRespository;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;

    @Override
    public void givePermission(String instantMessagingSoftware,String instantMessagingSoftwareUserId, String groupId) {
        manageRespository.save(new Manage(instantMessagingSoftware,instantMessagingSoftwareUserId,groupId));
    }

    @Override
    public boolean checkPermission(String instantMessagingSoftware,String instantMessagingSoftwareUserId, String groupId) {
        if(instantMessagingSoftwareUserId.equals(System.getenv("MIM_ADMIN_TOKEN"))) return true;
        List<Manage> manages = manageRespository.findAll();
        UserId userId = new UserId(instantMessagingSoftware,instantMessagingSoftwareUserId);
        for (Manage manage:manages)
            if(userService.checkManage(manage,userId)&&manage.getGroupIdForeignKey().equals(groupId))
                return true;
        return false;
    }
}
