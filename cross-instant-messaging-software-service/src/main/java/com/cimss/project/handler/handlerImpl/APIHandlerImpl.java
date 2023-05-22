package com.cimss.project.handler.handlerImpl;

import com.cimss.project.apibody.ManageBean;
import com.cimss.project.apibody.MessageBean;
import com.cimss.project.controller.exception.NoPermissionException;
import com.cimss.project.controller.exception.RequestNotFoundException;
import com.cimss.project.controller.exception.WrongFormatException;
import com.cimss.project.database.DatabaseService;
import com.cimss.project.database.entity.*;
import com.cimss.project.database.entity.token.GroupRole;
import com.cimss.project.security.JwtUtilities;
import com.cimss.project.handler.APIHandler;
import com.cimss.project.service.CIMSService;
import com.cimss.project.service.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cimss.project.database.entity.token.GroupRole.NOT_MEMBER;

@Service
public class APIHandlerImpl implements APIHandler {
    @Autowired
    private CIMSService cimsService;

    @Autowired
    private NotifyService notifyService;

    @Autowired
    private JwtUtilities jwtUtilities;
    @Autowired
    private DatabaseService databaseService;

    public GroupRole getGroupRole(String accessToken, String groupId){
        UserId userId = jwtUtilities.jwtDecode(accessToken);
        if(userId==null) return NOT_MEMBER;
        User user = databaseService.getUserById(userId);
        if(user==null) return NOT_MEMBER;
        Group group = databaseService.getGroupById(groupId);
        if(group==null) return NOT_MEMBER;
        MemberId memberId = MemberId.CreateMemberId(userId,groupId);
        if(memberId==null) return NOT_MEMBER;
        Member member = databaseService.getMemberById(memberId);
        if(member==null) return NOT_MEMBER;
        return member.getGroupRole();
    }
    //user
    @Override
    public ResponseEntity<MessageBean> homeTest() {
        return new ResponseEntity<>(MessageBean.CreateMessageBean("Hello!"), HttpStatus.OK);
    }

    @Override
    public List<Group.GroupData> searchGroup(String keyword) {
        return cimsService.searchGroup(keyword);
    }

    @Override
    public Group newGroup(ManageBean.NewGroupBean newGroupBean) {
        return cimsService.newGroup(Group.CreateGroup().copyFromObject(newGroupBean));
    }
    @Override
    public void sendTextMessage(String accessToken, ManageBean.SendBean sendBean) {
        cimsService.sendTextMessage(sendBean.getUserId(), sendBean.getMessage());
    }

    //manager
    @Override
    public List<Member.MemberData> getMembers(String accessToken, String groupId) {
        if(!getGroupRole(accessToken,groupId).managerPermission())
            throw new NoPermissionException(GroupRole.GROUP_MANAGER,getGroupRole(accessToken,groupId));
        return cimsService.getMembers(groupId);
    }

    @Override
    public Group groupDetail(String accessToken, String groupId) {
        if(!getGroupRole(accessToken,groupId).managerPermission())
            throw new NoPermissionException(GroupRole.GROUP_MANAGER,getGroupRole(accessToken,groupId));
        return cimsService.groupDetail(groupId);
    }

    @Override
    public void notifyTest(String accessToken, String groupId) {
        if(!getGroupRole(accessToken,groupId).managerPermission())
            throw new NoPermissionException(GroupRole.GROUP_MANAGER,getGroupRole(accessToken,groupId));
        notifyService.notifyTest(groupId);
    }

    @Override
    public void broadcast(String accessToken, ManageBean.BroadcastBean broadcastBean) {
        if(broadcastBean.getGroupId()==null) throw new RequestNotFoundException("groupId");
        if(broadcastBean.getMessage()==null) throw new RequestNotFoundException("message");
        if(!getGroupRole(accessToken,broadcastBean.getGroupId()).managerPermission())
            throw new NoPermissionException(GroupRole.GROUP_MANAGER,getGroupRole(accessToken,broadcastBean.getGroupId()));
        cimsService.broadcast(broadcastBean.getGroupId(), broadcastBean.getMessage(),broadcastBean.getIgnoreList());
    }

    @Override
    public void alterGroup(String accessToken, ManageBean.AlterGroupBean alterGroupBean) {
        if(!getGroupRole(accessToken,alterGroupBean.getGroupId()).managerPermission())
            throw new NoPermissionException(GroupRole.GROUP_MANAGER,getGroupRole(accessToken,alterGroupBean.getGroupId()));
        cimsService.alterGroup(Group.CreateEditGroup(alterGroupBean.getGroupId()).copyFromObject(alterGroupBean));
    }

    @Override
    public void alterList(String accessToken,ManageBean.FunctionListBean functionListBean) {
        if(!getGroupRole(accessToken,functionListBean.getGroupId()).managerPermission())
            throw new NoPermissionException(GroupRole.GROUP_MANAGER,getGroupRole(accessToken,functionListBean.getGroupId()));
        if(!functionListBean.getFunctionList().decodeTest("/"))
            throw new WrongFormatException();
        cimsService.alterGroup(Group.CreateEditGroup(functionListBean.getGroupId()).copyFromObject(functionListBean));
    }

    //owner
    @Override
    public void grantPermission(String accessToken, ManageBean.GrantPermissionBean grantPermissionBean) {
        if(!getGroupRole(accessToken,grantPermissionBean.getGroupId()).managerPermission())
            throw new NoPermissionException(GroupRole.GROUP_OWNER,getGroupRole(accessToken,grantPermissionBean.getGroupId()));
        cimsService.grantPermission(grantPermissionBean.getUserId(), grantPermissionBean.getGroupId());
    }

    @Override
    public void revokePermission(String accessToken, ManageBean.GrantPermissionBean grantPermissionBean) {
        if(!getGroupRole(accessToken,grantPermissionBean.getGroupId()).managerPermission())
            throw new NoPermissionException(GroupRole.GROUP_OWNER,getGroupRole(accessToken,grantPermissionBean.getGroupId()));
        cimsService.revokePermission(grantPermissionBean.getUserId(), grantPermissionBean.getGroupId());
    }

    @Override
    public void deleteGroup(String accessToken, String groupId) {
        if(!getGroupRole(accessToken,groupId).managerPermission())
            throw new NoPermissionException(GroupRole.GROUP_OWNER,getGroupRole(accessToken,groupId));
        cimsService.deleteGroup(groupId);
    }

    //admin
    @Override
    public void deleteAll(String accessToken) {
        databaseService.deleteAllData();
    }

    @Override
    public List<User> getUsers(String accessToken) {
        return databaseService.getAllUsers();
    }

    @Override
    public List<Group> getGroups(String accessToken) {
        return databaseService.getAllGroups();
    }

    @Override
    public List<Member> getMembers(String accessToken) {
        return databaseService.getAllMembers();
    }

    @Override
    public void broadcastAll(String accessToken, MessageBean messageBean) {
        cimsService.broadcastAll(messageBean.getMessage());
    }

}
