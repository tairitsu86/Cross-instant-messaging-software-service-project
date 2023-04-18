package com.cimss.project.database.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ManagerId implements Serializable {

    private String instantMessagingSoftwareForeignKey;
    private String instantMessagingSoftwareUserIdForeignKey;
    private String groupIdForeignKey;
}
