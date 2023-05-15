package com.cimss.project.database.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
@Getter
@Setter
public class MemberId implements Serializable {
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "instant_messaging_software_fk", referencedColumnName = "instant_messaging_software"),
            @JoinColumn(name = "instant_messaging_software_user_id_fk", referencedColumnName = "instant_messaging_software_user_id")
    })
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id_fk", referencedColumnName = "group_id")
    private Group group;
    public static MemberId CreateMemberId(UserId userId, String groupId){
        return new MemberId(User.CreateUser(userId),Group.CreateEditGroup(groupId));
    }
}
