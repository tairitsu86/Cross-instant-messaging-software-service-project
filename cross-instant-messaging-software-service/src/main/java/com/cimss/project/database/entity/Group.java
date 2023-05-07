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
@Table(name = "[GROUP]")
public class Group {
	@Schema(description = "The id of the group, composed of six alphanumerics.", example = "AbCd12")
	@Id
	private String groupId;
	@Schema(description = "Name of the group.",example = "My group")
	@Column
	private String groupName;
	@Schema(description = "Description of the group.",example = "This is my group!")
	@Column
	private String groupDescription;
	@Schema(description = "Webhook of the group.",example = "https://myWebService/cimssWebhook")
	@Column
	private String groupWebhook;
	@Schema(description = "The prefix of the command this group provide.",example = "myService")
	@Column
	private String groupKeyword;
	@Schema(description = "API key of the group.",example = "1b619a98-55b4-4d80-8f50-e7aa9fde8cc5")
	@Column
	private String authorizationKey;
	@Schema(description = "This group is public or private.",example = "true")
	@Column
	private Boolean isPublic;
	@Schema(description = "Can any user join this group by group id?",example = "true")
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
		@Schema(description = "The id of the group, composed of six alphanumerics.", example = "AbCd12")
		private String groupId;
		@Schema(description = "Name of the group.",example = "My group")
		private String groupName;
		@Schema(description = "Description of the group.",example = "This is my group!")
		private String groupDescription;
	}
	public static Group CreateServiceGroup(){
		return new Group(null,null,null,null,null, null,true,true);
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
