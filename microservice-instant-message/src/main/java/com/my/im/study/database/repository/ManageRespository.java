package com.my.im.study.database.repository;

import com.my.im.study.database.entity.Manage;
import com.my.im.study.database.entity.ManageId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManageRespository extends JpaRepository<Manage, ManageId> {
}
