package com.my.im.study.database.repository;

import com.my.im.study.database.entity.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

import com.my.im.study.database.entity.User;

public interface UserRepository extends JpaRepository<User, UserId> {
}
