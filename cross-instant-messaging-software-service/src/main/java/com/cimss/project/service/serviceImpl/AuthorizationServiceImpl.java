package com.cimss.project.service.serviceImpl;

import com.cimss.project.database.GroupService;
import com.cimss.project.database.MemberService;
import com.cimss.project.database.entity.User;
import com.cimss.project.database.entity.UserId;
import com.cimss.project.service.AuthorizationService;
import com.cimss.project.service.token.PermissionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    private static final String ADMIN_TOKEN = System.getenv("CIMSS_ADMIN_TOKEN");
    @Autowired
    private GroupService groupService;
    @Autowired
    private MemberService memberService;

    @Override
    public PermissionList authorization(String accessToken) {
        if(ADMIN_TOKEN==null) return PermissionList.NONE;
        if(ADMIN_TOKEN.equals(accessToken)) return PermissionList.ADMIN;
        return PermissionList.NONE;
    }
    @Override
    public PermissionList authorization(String accessToken, UserId userId) {
        String groupId = groupService.getGroupByAuthorizationKey(accessToken);
        if(groupId==null) return PermissionList.NONE;
        if(memberService.isMember(userId,groupId))
            return PermissionList.MANAGER;
        return PermissionList.NONE;
    }
    @Override
    public PermissionList authorization(String accessToken, String groupId) {
        if(accessToken.equals(groupService.getAuthorizationKey(groupId)))
            return PermissionList.MANAGER;
        return PermissionList.NONE;
    }
    @Override
    public PermissionList authorization(UserId executor,String groupId) {
        if(executor==null||groupId==null) return PermissionList.NONE;
        if(memberService.isManager(executor,groupId)) return PermissionList.MANAGER;
        return PermissionList.NONE;
    }
}
