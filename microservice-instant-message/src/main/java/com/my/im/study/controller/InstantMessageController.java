package com.my.im.study.controller;

import com.my.im.study.apibody.*;
import com.my.im.study.database.entity.Group;
import com.my.im.study.database.entity.User;
import com.my.im.study.service.AuthorizationService;
import com.my.im.study.service.CrossIMSService;
import com.my.im.study.service.WebhookService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "跨即時通訊軟體微服務API")
@RestController
public class InstantMessageController {
	
	@Autowired
    private CrossIMSService crossIMSService;

	@Autowired
	private WebhookService webhookService;

	@Autowired
	private AuthorizationService authorizationService;

	//get method

	@Hidden
	@GetMapping("/")
	public MessageBean home() {
		return MessageBean.CreateMessageBean("Hello!");
	}

	@Operation(summary = "測試webhook", description = "會發送一個測試事件給該群組的webhook")
	@GetMapping("/webhooktest")
	public MessageBean webhookTest(@RequestHeader(name = "Authorization") String accessToken,
								   @Parameter(description = "該群組的ID，由32位英數字組合的字串", example = "0123a012345678b0123456c012345678")
								   @RequestParam String groupId){
		if(!authorizationService.authorization(accessToken,"",groupId).managerPermission())
			return MessageBean.CreateAuthorizationWrongMessageBean();
		return MessageBean.CreateMessageBean(webhookService.testWebhook(groupId));
	}

	@Operation(summary = "搜尋群組", description = "透過keyword搜尋群組")
	@GetMapping("/searchgroup")
	public List<Group.GroupData> searchGroup(@Parameter(description = "任意文字", example = "group")
									   @RequestParam String keyword) {
		return crossIMSService.searchGroup(keyword);
	}
	@Hidden
	@Operation(summary = "系統廣播", description = "API KEY必須是ADMIN KEY，否則會驗證失敗")
	@GetMapping("/broadcastall")
	public MessageBean broadcastAll(@RequestHeader(name = "Authorization") String accessToken,
									@RequestParam String message) {
		if(!authorizationService.authorization(accessToken))
			return MessageBean.CreateAuthorizationWrongMessageBean();
		return MessageBean.CreateMessageBean(crossIMSService.broadcastAll(message));
	}

	//post method
	@Operation(summary = "寄文字訊息給指定使用者", description = "該使用者必須是API KEY對應群組內成員，否則會驗證失敗")
	@PostMapping("/send")
	public MessageBean sendTextMessage(@RequestHeader(name = "Authorization") String accessToken,
									   @RequestBody ManageBean.SendBean sendBean) {
		if(!authorizationService.authorization(accessToken,"", User.CreateNoNameUserBean(sendBean.getInstantMessagingSoftware(),sendBean.getInstantMessagingSoftwareUserId())).managerPermission())
			return MessageBean.CreateAuthorizationWrongMessageBean();
		return MessageBean.CreateMessageBean(crossIMSService.sendTextMessage(sendBean.getInstantMessagingSoftware(), sendBean.getInstantMessagingSoftwareUserId(), sendBean.getMessage()));
	}
	@Operation(summary = "群組廣播", description = "必須用該群組的API KEY，否則會驗證失敗")
	@PostMapping("/broadcast")
	public MessageBean broadcast(@RequestHeader(name = "Authorization") String accessToken,
								 @RequestBody ManageBean.BroadcastBean broadcastBean) {
		if(!authorizationService.authorization(accessToken,"", broadcastBean.getGroupId()).managerPermission())
			return MessageBean.CreateAuthorizationWrongMessageBean();
		return MessageBean.CreateMessageBean(crossIMSService.broadcast(broadcastBean.getGroupId(), broadcastBean.getMessage()));
	}
	@Operation(summary = "新增群組", description = "不用驗證，所有人都可以申請新群組")
	@PostMapping("/newgroup")
	public Group newGroup(@RequestBody ManageBean.NewGroupBean newGroupBean) {
		return crossIMSService.newGroup(newGroupBean.getGroupName(),newGroupBean.getGroupWebhook());
	}
	@Operation(summary = "群組重新命名", description = "必須用該群組的API KEY，否則會驗證失敗")
	@PostMapping("/renamegroup")
	public MessageBean renameGroup(@RequestBody ManageBean.RenameGroupBean renameGroupBean) {
		return MessageBean.CreateMessageBean(crossIMSService.renameGroup(renameGroupBean.getGroupId(),renameGroupBean.getGroupName()));
	}
	@Operation(summary = "將使用者加入群組", description = "必須用該群組的API KEY，否則會驗證失敗，該使用者必須傳送訊息給本系統任意chat bot過，否則不能加入")
	@PostMapping("/join")
	public MessageBean join(@RequestHeader(name = "Authorization") String accessToken,
							@RequestBody ManageBean.JoinBean joinBean) {
		if(!authorizationService.authorization(accessToken,"", joinBean.getGroupId()).managerPermission())
			return MessageBean.CreateAuthorizationWrongMessageBean();
		return MessageBean.CreateMessageBean(crossIMSService.join(joinBean.getInstantMessagingSoftware(), joinBean.getInstantMessagingSoftwareUserId(), joinBean.getGroupId()));
	}
	@Operation(summary = "設定群組webhook", description = "必須用該群組的API KEY，否則會驗證失敗")
	@PostMapping("/setwebhook")
	public MessageBean setWebhook(@RequestHeader(name = "Authorization") String accessToken,
								  @RequestBody ManageBean.SetWebhookBean setWebhookBean){
		if(!authorizationService.authorization(accessToken,"",setWebhookBean.getGroupId()).managerPermission())
			return MessageBean.CreateAuthorizationWrongMessageBean();
		return MessageBean.CreateMessageBean(webhookService.setWebhook(setWebhookBean.getGroupId(),setWebhookBean.getGroupWebhook()));
	}
	@Operation(summary = "設定群組管理員", description = "必須用該群組的API KEY，否則會驗證失敗")
	@PostMapping("/grantpermission")
	public MessageBean grantPermission(@RequestHeader(name = "Authorization") String accessToken,
									   @RequestBody ManageBean.GrantPermissionBean grantPermissionBean) {
		if(!authorizationService.authorization(accessToken,"", grantPermissionBean.getGroupId()).managerPermission())
			return MessageBean.CreateAuthorizationWrongMessageBean();
		return MessageBean.CreateMessageBean(crossIMSService.grantPermission(grantPermissionBean.getInstantMessagingSoftware(), grantPermissionBean.getInstantMessagingSoftwareUserId(), grantPermissionBean.getGroupId()));
	}
}