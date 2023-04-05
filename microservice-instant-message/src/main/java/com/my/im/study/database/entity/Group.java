package com.my.im.study.database.entity;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@Table(name = "[GROUP]")
public class Group {
	@Id
	@GeneratedValue(generator = "jpa-uuid")
	private String groupId;
	@Column
	private String groupName;
	@Column
	private String groupWebhook;
	@Column
	private String authorizationKey;

	public static Group CreateDataBean(Group group){
		return new Group(group.groupId,group.groupName,null,null);
	}
	public static Group CreateGroup(String groupName,String groupWebhook){
		return new Group(null,groupName,groupWebhook, UUID.randomUUID().toString());
	}
}
