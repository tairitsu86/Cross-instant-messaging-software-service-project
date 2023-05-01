package com.cimss.project.database.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@Table(name = "[GROUP]")
public class Group {
	@Schema(description = "該群組的ID，由6位英數字組合的字串", example = "AbCd12")
	@Id
	private String groupId;
	@Schema(description = "該群組的名字",example = "我ㄉ群組")
	@Column
	private String groupName;
	@Schema(description = "該群組的敘述",example = "這是我的群組")
	@Column
	private String groupDescription;
	@Schema(description = "該群組的Webhook",example = "https://myWebService/cimssWebhook")
	@Column
	private String groupWebhook;
	@Schema(description = "若文字訊息開頭符合關鍵字，將觸發Webhook寄送事件",example = "myService")
	@Column
	private String groupKeyword;
	@Schema(description = "該群組的API KEY",example = "1b619a98-55b4-4d80-8f50-e7aa9fde8cc5")
	@Column
	private String authorizationKey;
	@Schema(description = "該群組是否為公開(能被搜尋功能找到)群組?",example = "true")
	@Column
	private Boolean isPublic;
	@Schema(description = "其他使用者是否可以透過系統指令和group id加入此群組?",example = "true")
	@Column
	private Boolean joinById;
	public static GroupData CreateDataBean(Group group){
		return new GroupData(group.groupId,group.groupName,group.groupDescription);
	}
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class GroupData{
		@Schema(description = "該群組的ID，由6位英數字組合的字串", example = "AbCd12")
		private String groupId;
		@Schema(description = "該用戶的即時通訊軟體ID",example = "U11111111111111111111111111111111")
		private String groupName;
		@Schema(description = "該群組的敘述",example = "這是我的群組")
		private String groupDescription;
	}
	public static GroupDetail CreateDetailBean(Group group){
		return new GroupDetail(group.groupId,group.groupName,group.groupDescription, group.groupKeyword, group.isPublic,group.joinById);
	}
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class GroupDetail{
		@Schema(description = "該群組的ID，由6位英數字組合的字串", example = "AbCd12")
		private String groupId;
		@Schema(description = "該用戶的即時通訊軟體ID",example = "U11111111111111111111111111111111")
		private String groupName;
		@Schema(description = "該群組的敘述",example = "這是我的群組")
		private String groupDescription;
		@Schema(description = "若文字訊息開頭符合關鍵字，將觸發Webhook寄送事件",example = "myService")
		private String groupKeyword;
		@Schema(description = "該群組是否為公開(能被搜尋功能找到)群組?",example = "true")
		private Boolean isPublic;
		@Schema(description = "其他使用者是否可以透過系統指令和group id加入此群組?(對API無影響)",example = "true")
		private Boolean joinById;
	}
	public static Group CreateServiceGroup(String groupName){
		return new Group(null,groupName,null,null,null, null,true,true);
	}
	public static Group CreatePrivateGroup(String groupName){
		return new Group(null,groupName,null,null,null, null,false,true);
	}
	public static Group CreateEditGroup(String groupId){
		return new Group(groupId,null,null,null,null,null,null,null);
	}
	public Group copyFromObject(Object o){
		if(o==null){
			System.err.println("The object is null");
			return null;
		}
		ObjectMapper objectMapper = new ObjectMapper();
		Group newGroup = null;
		try {
			newGroup = objectMapper.readValue(objectMapper.writeValueAsString(o), Group.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		if(newGroup==null){
			System.err.println("Mapping fail");
			return null;
		}
		groupName = newGroup.groupName==null?groupName:newGroup.groupName;
		groupDescription = newGroup.groupDescription==null?groupDescription:newGroup.groupDescription;
		groupWebhook = newGroup.groupWebhook==null?groupWebhook: newGroup.groupWebhook;
		groupKeyword = newGroup.groupKeyword==null?groupKeyword:newGroup.groupKeyword;
		isPublic = newGroup.isPublic==null?isPublic:newGroup.isPublic;
		joinById = newGroup.joinById==null?joinById:newGroup.joinById;
		return this;
	}
}
