package com.my.im.study.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "[manage]")
@IdClass(ManageId.class)
public class Manage {
    @Id
    private String instantMessagingSoftwareForeignKey;
    @Id
    private String instantMessagingSoftwareUserIdForeignKey;
    @Id
    private String groupIdForeignKey;
}
