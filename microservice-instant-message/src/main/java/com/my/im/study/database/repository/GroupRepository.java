package com.my.im.study.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my.im.study.database.entity.Group;

public interface GroupRepository extends JpaRepository<Group, String> {
}
