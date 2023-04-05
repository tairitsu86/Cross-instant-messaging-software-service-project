package com.my.im.study.service;

import com.my.im.study.database.entity.Group;
import com.my.im.study.database.entity.User;

public interface AuthorizationService {
    boolean authorization(String accessToken);
    PermissionList authorization(String accessToken, String instantMessagingSoftware,User user);
    PermissionList authorization(String accessToken, String instantMessagingSoftware, String groupId);
}
