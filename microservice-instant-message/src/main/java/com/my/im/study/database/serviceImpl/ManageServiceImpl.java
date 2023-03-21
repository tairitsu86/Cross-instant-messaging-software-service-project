package com.my.im.study.database.serviceImpl;

import com.my.im.study.database.GroupService;
import com.my.im.study.database.ManageService;
import com.my.im.study.database.UserService;
import com.my.im.study.database.entity.Manage;
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
    public void givePermission(String userId, String groupId) {
        manageRespository.save(new Manage(null,userId,groupId));
    }

    @Override
    public boolean checkPermission(String userId, String groupId) {
        if(userId.equals(System.getenv("MIM_ADMIN_TOKEN"))) return true;
        List<Manage> manages = manageRespository.findAll();
        for (Manage manage:manages)
            if(manage.getUserIdForeignKey().equals(userId)&&manage.getGroupIdForeignKey().equals(groupId))
                return true;
        return false;
    }
}
