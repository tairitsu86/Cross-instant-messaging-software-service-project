package com.cimss.project.database.repository;

import com.cimss.project.database.entity.Member;
import com.cimss.project.database.entity.ManagerId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, ManagerId>{
}
