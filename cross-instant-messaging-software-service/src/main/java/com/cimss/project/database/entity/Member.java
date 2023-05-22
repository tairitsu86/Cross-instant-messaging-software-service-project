package com.cimss.project.database.entity;

import com.cimss.project.database.entity.token.GroupRole;
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
public class Member{
	@EmbeddedId
	private MemberId memberId;

	@Column
	@Enumerated(EnumType.STRING)
	private GroupRole groupRole;
	public static Member CreateMember(UserId userId, String groupId){
		return new Member(MemberId.CreateMemberId(userId,groupId), GroupRole.GROUP_MEMBER);
	}
	public MemberData toMemberData(){
		return new MemberData(memberId.getUser(), groupRole);
	}


	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class MemberData{
		@Schema(description = "Data of the user")
		private User user;
		@Schema(description = "Is this user is manager?")
		private GroupRole groupRole;
		public User toUser(){
			return user;
		}
	}
}	
