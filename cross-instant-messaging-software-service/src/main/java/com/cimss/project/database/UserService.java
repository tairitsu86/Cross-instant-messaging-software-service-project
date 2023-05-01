package com.cimss.project.database;

import java.util.List;

import com.cimss.project.database.entity.Member;
import com.cimss.project.database.entity.User;
import com.cimss.project.database.entity.UserId;

public interface UserService {
    User createUser(User user);

    User getUserById(String instantMessagingSoftware,String instantMessagingSoftwareUserId);

    List<User> getAllUsers();

    void deleteUser(String instantMessagingSoftware,String instantMessagingSoftwareUserId);

    boolean checkMember(Member member, UserId userId);

    void deleteAllUsers();

}
