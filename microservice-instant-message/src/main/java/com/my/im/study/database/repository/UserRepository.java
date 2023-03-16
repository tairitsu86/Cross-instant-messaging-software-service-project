package com.my.im.study.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my.im.study.database.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
}
