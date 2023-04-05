package com.my.im.study.service.serviceImpl;

import com.my.im.study.database.GroupService;
import com.my.im.study.database.ManagerService;
import com.my.im.study.database.MemberService;
import com.my.im.study.database.entity.Group;
import com.my.im.study.database.entity.User;
import com.my.im.study.service.AuthorizationService;
import com.my.im.study.service.PermissionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    private static final String ADMIN_TOKEN = System.getenv("MIM_ADMIN_TOKEN");
    @Autowired
    private GroupService groupService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ManagerService managerService;

    @Override
    public boolean authorization(String accessToken) {
        if(ADMIN_TOKEN==null) return false;
        return ADMIN_TOKEN.equals(accessToken);
    }
    public boolean authorization(String accessToken, User user) {
        String groupId = groupService.getGroupByAuthorizationKey(accessToken);
        if(groupId==null) return false;
        return memberService.isMember(user.getInstantMessagingSoftware(), user.getInstantMessagingSoftwareUserId(),groupId);
    }
    public boolean authorization(String accessToken, String groupId) {
        return accessToken.equals(groupService.getAuthorizationKey(groupId));
    }

    @Override
    public PermissionList authorization(String accessToken, String instantMessagingSoftware,User user) {
        if(accessToken==null||instantMessagingSoftware==null||user==null) return PermissionList.NONE;
        if(authorization(accessToken)) return PermissionList.ADMIN;
        if(authorization(accessToken,user)) return PermissionList.MANAGER;
        if(accessToken.equals(user.getInstantMessagingSoftwareUserId())&&instantMessagingSoftware.equals(user.getInstantMessagingSoftware()))
            return PermissionList.NORMAL;
        return PermissionList.NONE;
    }

    @Override
    public PermissionList authorization(String accessToken, String instantMessagingSoftware,String groupId) {
        if(accessToken==null||instantMessagingSoftware==null||groupId==null) return PermissionList.NONE;
        if(authorization(accessToken)) return PermissionList.ADMIN;
        if(authorization(accessToken,groupId)) return PermissionList.MANAGER;
        if(memberService.isMember(instantMessagingSoftware,accessToken,groupId)) return PermissionList.MANAGER;
        return PermissionList.NONE;
    }
}
