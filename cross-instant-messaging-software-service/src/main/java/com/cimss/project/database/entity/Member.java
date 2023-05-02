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
		return new MemberData(user.getInstantMessagingSoftware(),user.getInstantMessagingSoftwareUserId(), user.getUserName(), isManager);
	}
	public UserId toUserId(){
		return UserId.CreateUserId(instantMessagingSoftwareForeignKey,instantMessagingSoftwareUserIdForeignKey);
	}
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class MemberData{
		@Schema(description = "該用戶使用的即時通訊軟體全大寫英文名稱",example = "LINE")
		private String instantMessagingSoftware;
		@Schema(description = "該用戶的即時通訊軟體ID",example = "U11111111111111111111111111111111")
		private String instantMessagingSoftwareUserId;
		@Schema(description = "該用戶的名字",example = "王小明")
		private String userName;
		@Schema(description = "是否為管理員",example = "false")
		private Boolean isManager;
		public User toUser(){
			return new User(instantMessagingSoftware,instantMessagingSoftwareUserId,userName);
		}
	}
}	
