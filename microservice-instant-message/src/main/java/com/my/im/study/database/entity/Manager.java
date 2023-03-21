package com.my.im.study.database.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "[manage]")
@IdClass(ManagerId.class)
public class Manager {
    @Id
    private String instantMessagingSoftwareForeignKey;
    @Id
    private String instantMessagingSoftwareUserIdForeignKey;
    @Id
    private String groupIdForeignKey;
}
