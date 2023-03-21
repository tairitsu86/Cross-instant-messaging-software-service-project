package com.my.im.study.database;

import java.util.List;

import com.my.im.study.database.entity.Manage;
import com.my.im.study.database.entity.Member;
import com.my.im.study.database.entity.User;
import com.my.im.study.database.entity.UserId;

public interface UserService {
    User createUser(User user);

    User getUserById(String instantMessagingSoftware,String instantMessagingSoftwareUserId);

    List<User> getAllUsers();

    void deleteUser(String instantMessagingSoftware,String instantMessagingSoftwareUserId);

    boolean checkMember(Member member, UserId userId);
    boolean checkManage(Manage manage, UserId userId);

}
