package com.cimss.project.service;

import com.cimss.project.database.entity.User;
import com.cimss.project.service.token.PermissionList;

public interface AuthorizationService {
    boolean authorization(String accessToken);
    PermissionList authorization(String accessToken, String instantMessagingSoftware, User user);
    PermissionList authorization(String accessToken, String instantMessagingSoftware, String groupId);
}
