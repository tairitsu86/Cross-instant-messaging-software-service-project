package com.cimss.project.database.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class MemberId implements Serializable {

    private String instantMessagingSoftwareForeignKey;
    private String instantMessagingSoftwareUserIdForeignKey;
    private String groupIdForeignKey;
}
