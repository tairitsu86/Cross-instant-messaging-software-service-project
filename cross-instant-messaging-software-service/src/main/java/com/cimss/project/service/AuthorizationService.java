package com.cimss.project.service;

import com.cimss.project.database.entity.User;
import com.cimss.project.database.entity.UserId;
import com.cimss.project.service.token.PermissionList;

public interface AuthorizationService {
    PermissionList authorization(String accessToken);
    PermissionList authorization(String accessToken, UserId userId);
    PermissionList authorization(String accessToken, String groupId);
    PermissionList authorization(UserId executor, String groupId);
}
