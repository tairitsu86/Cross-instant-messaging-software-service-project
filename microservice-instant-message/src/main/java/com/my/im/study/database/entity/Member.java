package com.my.im.study.database.entity;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@Table(name = "[member]")
public class Member {
	@Id
	@GeneratedValue(generator = "jpa-uuid")
	private String memberId;
	@Column
	private String userIdForeignKey;
	@Column
	private String groupIdForeignKey;
}	
