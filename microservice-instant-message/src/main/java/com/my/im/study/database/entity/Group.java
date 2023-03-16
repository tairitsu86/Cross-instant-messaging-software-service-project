package com.my.im.study.database.entity;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
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
@Table(name = "[group]")
public class Group {
	@Id
	@GeneratedValue(generator = "jpa-uuid")
	private String groupId;
	@Column
	private String groupName;
}
