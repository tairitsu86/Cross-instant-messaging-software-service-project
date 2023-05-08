package com.cimss.project.controller;

import com.cimss.project.apibody.ManageBean;
import com.cimss.project.apibody.MessageBean;
import com.cimss.project.controller.exception.RequestNotFoundException;
import com.cimss.project.controller.exception.UnauthorizedException;
import com.cimss.project.database.entity.Group;
import com.cimss.project.database.entity.Member;
import com.cimss.project.service.AuthorizationService;
import com.cimss.project.service.CIMSService;
import com.cimss.project.service.WebhookService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Cross-IM Services API")
@RestController
public class CIMSSController {
	
	@Autowired
    private CIMSService cimsService;

	@Autowired
	private WebhookService webhookService;

	@Autowired
	private AuthorizationService authorizationService;

	//get method

	@Hidden
	@GetMapping("/")
	public ResponseEntity<MessageBean> home() {
		return new ResponseEntity<>(MessageBean.CreateMessageBean("Hello!"), HttpStatus.OK);
	}

	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Get data of all members", description = "Can get name,user id,the IM he/she used, and member kind.")
	@GetMapping("/groups/{groupId}/members")
	public List<Member.MemberData> getMembers(@RequestHeader(name = "Authorization") String accessToken,
								   @Parameter(description = "The id of the group, composed of six alphanumerics.", example = "AbCd12")
								 @PathVariable String groupId){
		if(!authorizationService.authorization(accessToken,groupId).managerPermission())
			throw new UnauthorizedException();
		return cimsService.getMembers(groupId);
	}
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Search group", description = "Search group by the keyword you provide.")
	@GetMapping("/groups/{keyword}/search")
	public List<Group.GroupData> searchGroup(@Parameter(description = "Any word", example = "my group")
											 @PathVariable String keyword) {
		return cimsService.searchGroup(keyword);
	}
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "The detail data of the group", description = "Get the group data in detail by group id.")
	@GetMapping("/groups/{groupId}/detail")
	public Group groupDetail(@RequestHeader(name = "Authorization") String accessToken,
							 @Parameter(description = "The id of the group, composed of six alphanumerics.", example = "AbCd12")
							 @PathVariable String groupId) {
		if(!authorizationService.authorization(accessToken,groupId).managerPermission())
			throw new UnauthorizedException();
		return cimsService.groupDetail(groupId);
	}

	//patch method
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Create new group", description = "Create your own group.")
	@PostMapping("/groups/new")
	public Group newGroup(@RequestBody ManageBean.NewGroupBean newGroupBean) {
		if(newGroupBean.getGroupName()==null) throw new RequestNotFoundException("groupName");
		return cimsService.newGroup(Group.CreateServiceGroup().copyFromObject(newGroupBean));
	}
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Send text message to order user", description = "The user must be in the group that api key mapping.")
	@PostMapping("/send/text")
	public void sendTextMessage(@RequestHeader(name = "Authorization") String accessToken,
								@NotNull @RequestBody ManageBean.SendBean sendBean) {
		if(!authorizationService.authorization(accessToken, sendBean.getUserId()).managerPermission())
			throw new UnauthorizedException();
		if(sendBean.getUserId()==null) throw new RequestNotFoundException("userId");
		if(sendBean.getMessage()==null) throw new RequestNotFoundException("message");
		cimsService.sendTextMessage(sendBean.getUserId(), sendBean.getMessage());
	}
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Webhook test", description = "Send a test event to the webhook of the group.")
	@PostMapping("/groups/{groupId}/webhook/test")
	public void webhookTest(@RequestHeader(name = "Authorization") String accessToken,
							@Parameter(description = "The id of the group, composed of six alphanumerics.", example = "AbCd12")
							@PathVariable String groupId){
		if(!authorizationService.authorization(accessToken,groupId).managerPermission())
			throw new UnauthorizedException();
		webhookService.testWebhook(groupId);
	}

	//Put method
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Broadcast text message", description = "Broadcast text message to everyone in the group.")
	@PutMapping("/broadcast/text")
	public void broadcast(@RequestHeader(name = "Authorization") String accessToken,
						  @NotNull @RequestBody ManageBean.BroadcastBean broadcastBean) {
		if(!authorizationService.authorization(accessToken, broadcastBean.getGroupId()).managerPermission())
			throw new UnauthorizedException();
		cimsService.broadcast(broadcastBean.getGroupId(), broadcastBean.getMessage());
	}


	//Patch method
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Alter the group attributes", description = "Request body must have groupId.")
	@PatchMapping("/groups/alter")
	public void alterGroup(@RequestHeader(name = "Authorization") String accessToken,
						   @NotNull @RequestBody ManageBean.AlterGroupBean alterGroupBean) {
		if(alterGroupBean.getGroupId()==null)
			throw new RequestNotFoundException("groupId");
		if(!authorizationService.authorization(accessToken, alterGroupBean.getGroupId()).managerPermission())
			throw new UnauthorizedException();
		cimsService.alterGroup(Group.CreateEditGroup(alterGroupBean.getGroupId()).copyFromObject(alterGroupBean));
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Grant permission", description = "Grant manager permission to any member.")
	@PatchMapping("/permission/grant")
	public void grantPermission(@RequestHeader(name = "Authorization") String accessToken,
								@NotNull @RequestBody ManageBean.GrantPermissionBean grantPermissionBean) {
		if(!authorizationService.authorization(accessToken, grantPermissionBean.getGroupId()).managerPermission())
			throw new UnauthorizedException();
		cimsService.grantPermission(grantPermissionBean.getUserId(), grantPermissionBean.getGroupId());
	}
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Revoke permission", description = "Revoke manager permission to any member.")
	@PatchMapping("/permission/revoke")
	public void revokePermission(@RequestHeader(name = "Authorization") String accessToken,
								 @NotNull @RequestBody ManageBean.GrantPermissionBean grantPermissionBean) {
		if(!authorizationService.authorization(accessToken, grantPermissionBean.getGroupId()).managerPermission())
			throw new UnauthorizedException();
		cimsService.revokePermission(grantPermissionBean.getUserId(), grantPermissionBean.getGroupId());
	}

	//delete method
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Delete group", description = "Delete the group by group id.")
	@DeleteMapping("/groups/{groupId}/delete")
	public void deleteGroup(@RequestHeader(name = "Authorization") String accessToken,
							@Parameter(description = "The id of the group, composed of six alphanumerics.", example = "AbCd12")
							@PathVariable String groupId) {
		if(!authorizationService.authorization(accessToken,groupId).managerPermission())
			throw new UnauthorizedException();
		cimsService.deleteGroup(groupId);
	}
}