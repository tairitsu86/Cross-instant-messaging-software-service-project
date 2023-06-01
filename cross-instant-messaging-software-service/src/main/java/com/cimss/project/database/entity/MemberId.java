package com.cimss.project.database.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
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

    @Override
    public boolean equals(Object obj) {
        if(this==obj) return true;
        if(!(obj instanceof MemberId)) return false;
        MemberId m = (MemberId) obj;
        return (Objects.equals(user, m.user))&&(Objects.equals(group, m.group));
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(user.getUserId())+Objects.hashCode(group.getGroupId());
    }
}
