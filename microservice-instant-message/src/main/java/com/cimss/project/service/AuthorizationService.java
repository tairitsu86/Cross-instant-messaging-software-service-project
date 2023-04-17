package com.cimss.project.service;

import com.cimss.project.database.entity.User;

public interface AuthorizationService {
    boolean authorization(String accessToken);
    PermissionList authorization(String accessToken, String instantMessagingSoftware,User user);
    PermissionList authorization(String accessToken, String instantMessagingSoftware, String groupId);
}
