package com.cimss.project.service.serviceImpl;

import com.cimss.project.controller.exception.DataNotFoundException;
import com.cimss.project.controller.exception.RequestNotFoundException;
import com.cimss.project.database.entity.Group;
import com.cimss.project.database.entity.UserId;
import com.cimss.project.service.AuthorizationService;
import com.cimss.project.service.CIMSService;
import com.cimss.project.service.token.PermissionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    private static final String ADMIN_TOKEN = System.getenv("CIMSS_ADMIN_TOKEN");
    @Autowired
    private CIMSService cimsService;

    @Override
    public PermissionList authorization(String accessToken) {
        if(ADMIN_TOKEN==null) return PermissionList.NONE;
        if(ADMIN_TOKEN.equals(accessToken)) return PermissionList.ADMIN;
        return PermissionList.NONE;
    }
    @Override
    public PermissionList authorization(String accessToken, UserId userId) {
        if(authorization(accessToken)==PermissionList.ADMIN)
            return PermissionList.ADMIN;
        if(userId==null) throw new RequestNotFoundException("userId");
        return PermissionList.NONE;
    }
    @Override
    public PermissionList authorization(String accessToken, String groupId) {
        if(authorization(accessToken)==PermissionList.ADMIN)
            return PermissionList.ADMIN;
        if(groupId==null) throw new RequestNotFoundException("groupId");
        Group group = cimsService.getGroupById(groupId);
        if(group==null) throw new DataNotFoundException("groupId",groupId);
        return PermissionList.NONE;
    }
    @Override
    public PermissionList authorization(UserId executor,String groupId) {
        if(executor==null||groupId==null) return PermissionList.NONE;
        if(cimsService.isManager(executor,groupId)) return PermissionList.MANAGER;
        return PermissionList.NONE;
    }
}
