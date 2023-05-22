package com.cimss.project.service;

import com.cimss.project.database.entity.UserId;
import com.cimss.project.im.ButtonList;

public interface CreateButtonListService {
    ButtonList createCIMSSMenu();
    ButtonList exitMenu();
    ButtonList createProfileMenu(UserId userId);
    ButtonList createGroupMenu(UserId userId,String groupId);
    ButtonList createGroupServiceMenu(String groupId,String path);
    ButtonList createGroupManageMenu(UserId userId,String groupId);
    ButtonList createGroupManageDetailMenu(UserId userId,String groupId);
    ButtonList createGroupManageDetailPropertyMenu(UserId userId,String groupId,String property);
    ButtonList createGroupManageAlterMemberMenu(UserId userId, String groupId);
}
