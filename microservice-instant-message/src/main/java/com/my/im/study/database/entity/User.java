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
public class User {
	@Id
	private String userId;
	@Column
	private String userName;
	@Column
	private String instantMessagingSoftware;
	@Column
	private String instantMessagingSoftwareUserId;
}
