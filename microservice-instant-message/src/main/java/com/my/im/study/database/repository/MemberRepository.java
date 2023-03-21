package com.my.im.study.database.repository;

import com.my.im.study.database.entity.ManageId;
import org.springframework.data.jpa.repository.JpaRepository;

import com.my.im.study.database.entity.Member;

public interface MemberRepository extends JpaRepository<Member, ManageId>{
}
