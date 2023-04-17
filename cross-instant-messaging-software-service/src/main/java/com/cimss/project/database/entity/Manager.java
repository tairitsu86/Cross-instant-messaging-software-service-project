package com.cimss.project.database.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "[MANAGER]")
@IdClass(ManagerId.class)
public class Manager {
    @Id
    private String instantMessagingSoftwareForeignKey;
    @Id
    private String instantMessagingSoftwareUserIdForeignKey;
    @Id
    private String groupIdForeignKey;
}
