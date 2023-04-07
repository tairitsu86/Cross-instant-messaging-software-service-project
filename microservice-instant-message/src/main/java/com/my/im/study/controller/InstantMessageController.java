package com.my.im.study.controller;

import com.my.im.study.apibody.*;
import com.my.im.study.database.entity.Group;
import com.my.im.study.database.entity.User;
import com.my.im.study.service.AuthorizationService;
import com.my.im.study.service.CrossIMSService;
import com.my.im.study.service.WebhookService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InstantMessageController {
	
	@Autowired
    private CrossIMSService crossIMSService;

	@Autowired
	private WebhookService webhookService;

	@Autowired
	private AuthorizationService authorizationService;

	//get method

	@Operation(summary = "Home test")
	@GetMapping("/")
	public MessageBean home() {
		return MessageBean.CreateMessageBean("Hello!");
	}

	@GetMapping("/webhooktest")
	public MessageBean webhookTest(@RequestHeader(name = "Authorization") String accessToken,
								   @RequestParam String groupId){
		if(!authorizationService.authorization(accessToken,"",groupId).managerPermission())
			return MessageBean.CreateAuthorizationWrongMessageBean();
		webhookService.testWebhook(groupId);
		return MessageBean.CreateMessageBean("Success");
	}

	@GetMapping("/searchgroup")
	public List<Group> searchGroup(@RequestParam String keyWord) {
		return crossIMSService.searchGroup(keyWord);
	}

	//post method
	@Operation(summary = "Send text message")
	@PostMapping("/send")
	public MessageBean sendTextMessage(@RequestHeader(name = "Authorization") String accessToken,
									   @RequestBody ManageBean manageBean) {
		if(!authorizationService.authorization(accessToken,"", User.CreateNoNameUserBean(manageBean.getInstantMessagingSoftware(),manageBean.getInstantMessagingSoftwareUserId())).managerPermission())
			return MessageBean.CreateAuthorizationWrongMessageBean();
		return MessageBean.CreateMessageBean(crossIMSService.sendTextMessage(manageBean.getInstantMessagingSoftware(), manageBean.getInstantMessagingSoftwareUserId(), manageBean.getMessage()));
	}
	@PostMapping("/broadcast")
	public MessageBean broadcast(@RequestHeader(name = "Authorization") String accessToken,
								 @RequestBody ManageBean manageBean) {
		if(!authorizationService.authorization(accessToken,"", manageBean.getGroupId()).managerPermission())
			return MessageBean.CreateAuthorizationWrongMessageBean();
		return MessageBean.CreateMessageBean(crossIMSService.broadcast(manageBean.getGroupId(), manageBean.getMessage()));
	}
	@PostMapping("/broadcastall")
	public MessageBean broadcastAll(@RequestHeader(name = "Authorization") String accessToken,
								 	@RequestBody ManageBean manageBean) {
		if(!authorizationService.authorization(accessToken))
			return MessageBean.CreateAuthorizationWrongMessageBean();
		return MessageBean.CreateMessageBean(crossIMSService.broadcastAll(manageBean.getMessage()));
	}
	@PostMapping("/newgroup")
	public Group newGroup(@RequestBody ManageBean manageBean) {
		return crossIMSService.newGroup(manageBean.getGroupName(),manageBean.getGroupWebhook());
	}

	@PostMapping("/renamegroup")
	public Group renameGroup(@RequestBody ManageBean manageBean) {
		return crossIMSService.renameGroup(manageBean.getGroupId(),manageBean.getGroupName());
	}
	@PostMapping("/join")
	public MessageBean join(@RequestHeader(name = "Authorization") String accessToken,
							@RequestBody ManageBean manageBean) {
		if(!authorizationService.authorization(accessToken,"", manageBean.getGroupId()).managerPermission())
			return MessageBean.CreateAuthorizationWrongMessageBean();
		return MessageBean.CreateMessageBean(crossIMSService.join(manageBean.getInstantMessagingSoftware(), manageBean.getInstantMessagingSoftwareUserId(), manageBean.getGroupId()));
	}
	@PostMapping("/setwebhook")
	public MessageBean setWebhook(@RequestHeader(name = "Authorization") String accessToken,
								  @RequestBody ManageBean manageBean){
		if(!authorizationService.authorization(accessToken,"",manageBean.getGroupId()).managerPermission())
			return MessageBean.CreateAuthorizationWrongMessageBean();
		webhookService.setWebhook(manageBean.getGroupId(),manageBean.getGroupWebhook());
		return MessageBean.CreateMessageBean("Success");
	}
	@PostMapping("/grantpermission")
	public MessageBean grantPermission(@RequestHeader(name = "Authorization") String accessToken,
									   @RequestBody ManageBean manageBean) {
		if(!authorizationService.authorization(accessToken,"", manageBean.getGroupId()).managerPermission())
			return MessageBean.CreateAuthorizationWrongMessageBean();
		return MessageBean.CreateMessageBean(crossIMSService.grantPermission(manageBean.getInstantMessagingSoftware(), manageBean.getInstantMessagingSoftwareUserId(), manageBean.getGroupId()));
	}
}