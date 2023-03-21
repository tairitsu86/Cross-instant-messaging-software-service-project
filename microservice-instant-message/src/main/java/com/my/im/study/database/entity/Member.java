package com.my.im.study.database.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

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
@Table(name = "[member]")
@IdClass(MemberId.class)
public class Member {
	@Id
	private String instantMessagingSoftwareForeignKey;
	@Id
	private String instantMessagingSoftwareUserIdForeignKey;
	@Id
	private String groupIdForeignKey;
}	
