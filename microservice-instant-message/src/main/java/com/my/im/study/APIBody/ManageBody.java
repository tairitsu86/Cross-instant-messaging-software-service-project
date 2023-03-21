package com.my.im.study.APIBody;

import com.my.im.study.database.entity.Group;
import com.my.im.study.database.entity.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ManageBody {
    private String command;
    private String group_id;
    private String group_name;
    private List<Group> group_list;
    private String user_id;
    private String user_name;
    private String instantMessagingSoftwareUserId;
    private List<User> user_list;
    private String message;
}
