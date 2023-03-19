package com.my.im.study.database;

import java.util.List;

import com.my.im.study.database.entity.User;

public interface UserService {
    User createUser(User user);

    User getUserById(String userId);

    List<User> getAllUsers();

    User updateUser(User user);

    void deleteUser(String userId);
}
