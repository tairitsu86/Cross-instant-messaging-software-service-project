package com.cimss.project.database.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Table(name = "[MEMBER]")
@JsonInclude(JsonInclude.Include.NON_NULL)
@IdClass(MemberId.class)
public class Member {
	@Id
	private String instantMessagingSoftwareForeignKey;
	@Id
	private String instantMessagingSoftwareUserIdForeignKey;
	@Id
	private String groupIdForeignKey;
	@Column
	private Boolean isManager;
	public static Member CreateNewMember(UserId userId, String groupId){
		return new Member(userId.getInstantMessagingSoftware(), userId.getInstantMessagingSoftwareUserId(), groupId,false);
	}
	public static MemberData CreateMemberData(User user,Boolean isManager){
		return new MemberData(user.toUserId(), user.getUserName(), isManager);
	}
	public UserId toUserId(){
		return UserId.CreateUserId(instantMessagingSoftwareForeignKey,instantMessagingSoftwareUserIdForeignKey);
	}
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class MemberData{
		@Schema(description = "Data of the user")
		private UserId userId;
		@Schema(description = "User name",example = "David")
		private String userName;
		@Schema(description = "Is manager",example = "false")
		private Boolean isManager;
		public User toUser(){
			return User.CreateByUserId(userId,userName);
		}
	}
}	
