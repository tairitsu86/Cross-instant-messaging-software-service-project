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
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "[USER]")
@IdClass(UserId.class)
public class User {
	@Id
	@Schema(description = "該用戶使用的即時通訊軟體全大寫英文名稱",example = "LINE")
	private String instantMessagingSoftware;
	@Id
	@Schema(description = "該用戶的即時通訊軟體ID",example = "U11111111111111111111111111111111")
	private String instantMessagingSoftwareUserId;
	@Column
	@Schema(description = "該用戶的名字",example = "王小明")
	private String userName;
	public static User CreateNoNameUserBean(String instantMessagingSoftware,String instantMessagingSoftwareUserId){
		return new User(instantMessagingSoftware,instantMessagingSoftwareUserId,null);
	}
}
