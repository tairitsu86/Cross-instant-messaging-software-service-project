package com.cimss.project.database.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "[USER]")
@IdClass(UserId.class)
public class User {
	@Id
	private String instantMessagingSoftware;
	@Id
	private String instantMessagingSoftwareUserId;
	@Column
	private String userName;
	public static User CreateNoNameUserBean(String instantMessagingSoftware,String instantMessagingSoftwareUserId){
		return new User(instantMessagingSoftware,instantMessagingSoftwareUserId,null);
	}
}
