package com.my.im.study.apibody;

import com.my.im.study.database.entity.Group;
import com.my.im.study.database.entity.Manager;
import com.my.im.study.database.entity.Member;
import com.my.im.study.database.entity.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ManageBean {
    private String command;
    private String groupId;
    private String groupName;
    private String groupWebhook;
    private List<Group> groupList;
    private String userName;
    private String instantMessagingSoftware;
    private String instantMessagingSoftwareUserId;
    private List<User> userList;
    private List<Member> memberList;
    private List<Manager> managerList;
    private String message;
    public ManageBean(String message){
        this.message = message;
    }
}
