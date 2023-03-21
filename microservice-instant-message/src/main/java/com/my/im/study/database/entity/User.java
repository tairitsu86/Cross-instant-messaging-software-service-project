package com.my.im.study.database.entity;

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
@Table(name = "[user]")
@IdClass(UserId.class)
public class User {
	@Id
	private String instantMessagingSoftware;
	@Id
	private String instantMessagingSoftwareUserId;
	@Column
	private String userName;
}
