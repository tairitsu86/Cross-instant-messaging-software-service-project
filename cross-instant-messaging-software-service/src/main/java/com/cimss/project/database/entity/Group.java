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
@Table(name = "CIMSS_GROUP")
public class Group {
	@Schema(description = "The id of the group, composed of six alphanumerics.", example = "AbCd12")
	@Id
	@Column(name = "group_id")
	private String groupId;
	@Schema(description = "Name of the group.",example = "My group")
	@Column(nullable=false)
	private String groupName;
	@Schema(description = "Description of the group.",example = "This is my group!")
	@Column
	private String groupDescription;
	@Schema(description = "The delivery mode of this group.",example = "NONE")
	@Column(nullable=false)
	private String deliveryMode;
	@Schema(description = "The function list of this group.",example = "{}")
	@Column(columnDefinition = "json")
	private String functionList;
	@Schema(description = "Is this group public?",example = "false")
	@Column(nullable=false)
	private Boolean isPublic;
	@Schema(description = "Can any user join this group by group id?",example = "false")
	@Column(nullable=false)
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
	public static Group CreateGroup(){
		return new Group(null,null,"None","NONE",null,true,true);
	}
	public static Group CreateGroup(String groupName){
		return new Group(null,groupName,null,null,null,false,true);
	}
	public static Group CreateEditGroup(String groupId){
		return new Group(groupId,null,null,null,null,null,null);
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
		functionList = newGroup.functionList==null?functionList:newGroup.functionList;
		deliveryMode = newGroup.deliveryMode==null?deliveryMode:newGroup.deliveryMode;
		isPublic = newGroup.isPublic==null?isPublic:newGroup.isPublic;
		joinById = newGroup.joinById==null?joinById:newGroup.joinById;
		return this;
	}
}
