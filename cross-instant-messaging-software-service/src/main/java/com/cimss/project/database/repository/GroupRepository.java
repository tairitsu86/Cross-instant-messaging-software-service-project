package com.cimss.project.database.repository;

import com.cimss.project.database.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, String> {
}
