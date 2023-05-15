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
@Table(name = "CIMSS_MEMBER")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Member {
	@EmbeddedId
	private MemberId memberId;

	@Column
	private Boolean isManager;
	public static Member CreateNewMember(UserId userId, String groupId){
		return new Member(MemberId.CreateMemberId(userId,groupId),false);
	}
	public static MemberData CreateMemberData(User user,Boolean isManager){
		return new MemberData(user, isManager);
	}
	public MemberData toMemberData(){
		return CreateMemberData(memberId.getUser(),isManager);
	}
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class MemberData{
		@Schema(description = "Data of the user")
		private User user;
		@Schema(description = "Is this user is manager?")
		private Boolean isManager;
		public User toUser(){
			return user;
		}
	}
}	
