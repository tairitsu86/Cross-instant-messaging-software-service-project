package com.cimss.project.database;

import java.util.List;

import com.cimss.project.database.entity.Member;
import com.cimss.project.database.entity.User;
import com.cimss.project.database.entity.UserId;

public interface UserService {
    User createUser(User user);

    void alterUserDisplayName(UserId userId, String newName);
    User getUserById(UserId userId);
    List<User> getAllUsers();
    void deleteAllUsers();
    boolean isUserExist(UserId userId);
}
