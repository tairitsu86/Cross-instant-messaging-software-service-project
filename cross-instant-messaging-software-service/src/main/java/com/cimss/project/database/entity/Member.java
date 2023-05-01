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
	public UserId toUserId(){
		return UserId.CreateUserId(instantMessagingSoftwareForeignKey,instantMessagingSoftwareUserIdForeignKey);
	}
}	
