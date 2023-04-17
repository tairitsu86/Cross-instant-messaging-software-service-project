package com.cimss.project.database.repository;

import com.cimss.project.database.entity.Manager;
import com.cimss.project.database.entity.ManagerId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRespository extends JpaRepository<Manager, ManagerId> {
}
