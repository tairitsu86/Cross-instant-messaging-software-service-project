package com.cimss.project.database.repository;

import com.cimss.project.database.entity.User;
import com.cimss.project.database.entity.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UserId> {
}
