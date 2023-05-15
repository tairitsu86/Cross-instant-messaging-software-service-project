package com.cimss.project.database.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Columns;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "CIMSS_USER")
public class User {
	@EmbeddedId
	private UserId userId;
	@Schema(description = "User name",example = "David")
	@Column
	private String userName;
	public static User CreateUser(UserId userId, String userName){
		return new User(userId, userName);
	}
	public static User CreateUser(UserId userId){
		return new User(userId, "");
	}
	public UserId toUserId(){
		return userId;
	}
}
