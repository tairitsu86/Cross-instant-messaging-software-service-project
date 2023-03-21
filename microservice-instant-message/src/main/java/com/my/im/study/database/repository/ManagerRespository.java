package com.my.im.study.database.repository;

import com.my.im.study.database.entity.Manager;
import com.my.im.study.database.entity.ManagerId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRespository extends JpaRepository<Manager, ManagerId> {
}
