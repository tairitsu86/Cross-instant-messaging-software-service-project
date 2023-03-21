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
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@Table(name = "[manage]")
public class Manage {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    private String manageId;
    @Column
    private String userIdForeignKey;
    @Column
    private String groupIdForeignKey;
}
