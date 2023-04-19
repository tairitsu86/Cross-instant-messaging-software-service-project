package com.cimss.project.controller;

import com.cimss.project.apibody.ManageBean;
import com.cimss.project.apibody.MessageBean;
import com.cimss.project.database.entity.Group;
import com.cimss.project.database.entity.User;
import com.cimss.project.service.AuthorizationService;
import com.cimss.project.service.CIMSService;
import com.cimss.project.service.WebhookService;
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
    private CIMSService cimsService;

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
								   @Parameter(description = "該群組的ID，由6位英數字組合的字串", example = "AbCd12")
								   @RequestParam String groupId){
		if(!authorizationService.authorization(accessToken,"",groupId).managerPermission())
			return MessageBean.CreateAuthorizationWrongMessageBean();
		return MessageBean.CreateMessageBean(webhookService.testWebhook(groupId));
	}

	@Operation(summary = "取得用戶資料", description = "必須用該群組的API KEY，否則會驗證失敗(無回傳)")
	@GetMapping("/getmembers")
	public List<User> getMembers(@RequestHeader(name = "Authorization") String accessToken,
								 @Parameter(description = "該群組的ID，由6位英數字組合的字串", example = "AbCd12")
								 @RequestParam String groupId){
		if(!authorizationService.authorization(accessToken,"",groupId).managerPermission())
			return null;
		return cimsService.getMembers(groupId);
	}

	@Operation(summary = "搜尋群組", description = "透過keyword搜尋群組")
	@GetMapping("/searchgroup")
	public List<Group.GroupData> searchGroup(@Parameter(description = "任意文字", example = "group")
											 @RequestParam String keyword) {
		return cimsService.searchGroup(keyword);
	}
	@Hidden
	@GetMapping("/broadcastall")
	public MessageBean broadcastAll(@RequestHeader(name = "Authorization") String accessToken,
									@RequestParam String message) {
		if(!authorizationService.authorization(accessToken))
			return MessageBean.CreateAuthorizationWrongMessageBean();
		return MessageBean.CreateMessageBean(cimsService.broadcastAll(message));
	}

	//post method
	@Operation(summary = "寄文字訊息給指定使用者", description = "該使用者必須是API KEY對應群組內成員，否則會驗證失敗")
	@PostMapping("/send")
	public MessageBean sendTextMessage(@RequestHeader(name = "Authorization") String accessToken,
									   @RequestBody ManageBean.SendBean sendBean) {
		if(!authorizationService.authorization(accessToken,"", User.CreateNoNameUserBean(sendBean.getInstantMessagingSoftware(),sendBean.getInstantMessagingSoftwareUserId())).managerPermission())
			return MessageBean.CreateAuthorizationWrongMessageBean();
		return MessageBean.CreateMessageBean(cimsService.sendTextMessage(sendBean.getInstantMessagingSoftware(), sendBean.getInstantMessagingSoftwareUserId(), sendBean.getMessage()));
	}
	@Operation(summary = "群組廣播", description = "必須用該群組的API KEY，否則會驗證失敗")
	@PostMapping("/broadcast")
	public MessageBean broadcast(@RequestHeader(name = "Authorization") String accessToken,
								 @RequestBody ManageBean.BroadcastBean broadcastBean) {
		if(!authorizationService.authorization(accessToken,"", broadcastBean.getGroupId()).managerPermission())
			return MessageBean.CreateAuthorizationWrongMessageBean();
		return MessageBean.CreateMessageBean(cimsService.broadcast(broadcastBean.getGroupId(), broadcastBean.getMessage()));
	}
	@Operation(summary = "新增群組", description = "不用驗證，所有人都可以申請新群組")
	@PostMapping("/newgroup")
	public Group newGroup(@RequestBody ManageBean.NewGroupBean newGroupBean) {
		Group newGroup = Group.CreateServiceGroup(newGroupBean.getGroupName());
		newGroup.copyFromObject(newGroupBean);
		return cimsService.newGroup(newGroup);
	}
	@Operation(summary = "更改群組屬性", description = "必須用該群組的API KEY，否則會驗證失敗")
	@PostMapping("/altergroup")
	public MessageBean alterGroup(@RequestHeader(name = "Authorization") String accessToken,
								  @RequestBody ManageBean.AlterGroupBean alterGroupBean) {
		if(!authorizationService.authorization(accessToken,"", alterGroupBean.getGroupId()).managerPermission())
			return MessageBean.CreateAuthorizationWrongMessageBean();
		Group alterGroup = Group.CreateEditGroup(alterGroupBean.getGroupId());
		alterGroup.copyFromObject(alterGroupBean);
		return MessageBean.CreateMessageBean(cimsService.alterGroup(alterGroup));
	}
	@Operation(summary = "將使用者加入群組", description = "必須用該群組的API KEY，否則會驗證失敗，該使用者必須傳送訊息給本系統任意chat bot過，否則不能加入")
	@PostMapping("/add")
	public MessageBean add(@RequestHeader(name = "Authorization") String accessToken,
							@RequestBody ManageBean.AddBean addBean) {
		if(!authorizationService.authorization(accessToken,"", addBean.getGroupId()).managerPermission())
			return MessageBean.CreateAuthorizationWrongMessageBean();
		return MessageBean.CreateMessageBean(cimsService.join(addBean.getInstantMessagingSoftware(), addBean.getInstantMessagingSoftwareUserId(), addBean.getGroupId()));
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
		return MessageBean.CreateMessageBean(cimsService.grantPermission(grantPermissionBean.getInstantMessagingSoftware(), grantPermissionBean.getInstantMessagingSoftwareUserId(), grantPermissionBean.getGroupId()));
	}
}