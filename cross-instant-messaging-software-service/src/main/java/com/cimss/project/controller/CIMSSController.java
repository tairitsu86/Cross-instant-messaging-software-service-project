package com.cimss.project.controller;

import com.cimss.project.apibody.ManageBean;
import com.cimss.project.apibody.MessageBean;
import com.cimss.project.controller.exception.DataNotFoundException;
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

@Tag(name = "跨即時通訊軟體微服務API")
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
	@Operation(summary = "取得用戶資料", description = "必須用該群組的API KEY，否則會驗證失敗(無回傳)")
	@GetMapping("/groups/{groupId}/members")
	public List<Member.MemberData> getMembers(@RequestHeader(name = "Authorization") String accessToken,
								   @Parameter(description = "該群組的ID，由6位英數字組合的字串", example = "AbCd12")
								 @PathVariable String groupId){
		if(!authorizationService.authorization(accessToken,groupId).managerPermission())
			throw new UnauthorizedException();
		return cimsService.getMembers(groupId);
	}

	//post method
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "新增群組", description = "不用驗證，所有人都可以申請新群組")
	@PostMapping("/groups/new")
	public Group newGroup(@RequestBody ManageBean.NewGroupBean newGroupBean) {
		if(newGroupBean.getGroupName()==null) throw new RequestNotFoundException("groupName");
		return cimsService.newGroup(Group.CreateServiceGroup().copyFromObject(newGroupBean));
	}
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "寄文字訊息給指定使用者", description = "該使用者必須是API KEY對應群組內成員，否則會驗證失敗")
	@PostMapping("/send/text")
	public void sendTextMessage(@RequestHeader(name = "Authorization") String accessToken,
								@NotNull @RequestBody ManageBean.SendBean sendBean) {
		if(!authorizationService.authorization(accessToken, sendBean.getUserId()).managerPermission())
			throw new UnauthorizedException();
		if(sendBean.getUserId()==null) throw new RequestNotFoundException("userId");
		if(sendBean.getMessage()==null) throw new RequestNotFoundException("message");
		cimsService.sendTextMessage(sendBean.getUserId(), sendBean.getMessage());
	}
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "搜尋群組", description = "透過keyword搜尋群組")
	@GetMapping("/groups/{keyword}/search")
	public List<Group.GroupData> searchGroup(@Parameter(description = "任意文字", example = "my group")
											 @PathVariable String keyword) {
		return cimsService.searchGroup(keyword);
	}
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "測試webhook", description = "會發送一個測試事件給該群組的webhook")
	@PostMapping("/groups/{groupId}/webhook/test")
	public void webhookTest(@RequestHeader(name = "Authorization") String accessToken,
							@Parameter(description = "該群組的ID，由6位英數字組合的字串", example = "AbCd12")
							@PathVariable String groupId){
		if(!authorizationService.authorization(accessToken,groupId).managerPermission())
			throw new UnauthorizedException();
		webhookService.testWebhook(groupId);
	}
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "群組詳細資料", description = "透過group id獲得該群組的詳細資料")
	@GetMapping("/groups/{groupId}/detail")
	public Group groupDetail(@RequestHeader(name = "Authorization") String accessToken,
							 @Parameter(description = "該群組的ID，由6位英數字組合的字串", example = "AbCd12")
							 @PathVariable String groupId) {
		if(!authorizationService.authorization(accessToken,groupId).managerPermission())
			throw new UnauthorizedException();
		return cimsService.groupDetail(groupId);
	}



	//put method
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "更改群組屬性", description = "groupId必填，其餘填修改項即可")
	@PutMapping("/groups/alter")
	public void alterGroup(@RequestHeader(name = "Authorization") String accessToken,
						   @NotNull @RequestBody ManageBean.AlterGroupBean alterGroupBean) {
		if(alterGroupBean.getGroupId()==null)
			throw new RequestNotFoundException("groupId");
		if(!authorizationService.authorization(accessToken, alterGroupBean.getGroupId()).managerPermission())
			throw new UnauthorizedException();
		cimsService.alterGroup(Group.CreateEditGroup(alterGroupBean.getGroupId()).copyFromObject(alterGroupBean));
	}
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "群組廣播文字訊息", description = "廣播文字訊息給所有成員")
	@PutMapping("/broadcast/text")
	public void broadcast(@RequestHeader(name = "Authorization") String accessToken,
						  @NotNull @RequestBody ManageBean.BroadcastBean broadcastBean) {
		if(!authorizationService.authorization(accessToken, broadcastBean.getGroupId()).managerPermission())
			throw new UnauthorizedException();
		cimsService.broadcast(broadcastBean.getGroupId(), broadcastBean.getMessage());
	}
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "賦予成員管理員權限", description = "必須用該群組的API KEY，否則會驗證失敗")
	@PutMapping("/permission/grant")
	public void grantPermission(@RequestHeader(name = "Authorization") String accessToken,
								@NotNull @RequestBody ManageBean.GrantPermissionBean grantPermissionBean) {
		if(!authorizationService.authorization(accessToken, grantPermissionBean.getGroupId()).managerPermission())
			throw new UnauthorizedException();
		cimsService.grantPermission(grantPermissionBean.getUserId(), grantPermissionBean.getGroupId());
	}
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "移除成員管理員權限", description = "必須用該群組的API KEY，否則會驗證失敗")
	@PutMapping("/permission/revoke")
	public void revokePermission(@RequestHeader(name = "Authorization") String accessToken,
								 @NotNull @RequestBody ManageBean.GrantPermissionBean grantPermissionBean) {
		if(!authorizationService.authorization(accessToken, grantPermissionBean.getGroupId()).managerPermission())
			throw new UnauthorizedException();
		cimsService.revokePermission(grantPermissionBean.getUserId(), grantPermissionBean.getGroupId());
	}

	//delete method
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "刪除群組", description = "不可逆操作，請小心使用")
	@DeleteMapping("/groups/{groupId}/delete")
	public void deleteGroup(@RequestHeader(name = "Authorization") String accessToken,
							@Parameter(description = "該群組的ID，由6位英數字組合的字串", example = "AbCd12")
							@PathVariable String groupId) {
		if(!authorizationService.authorization(accessToken,groupId).managerPermission())
			throw new UnauthorizedException();
		cimsService.deleteGroup(groupId);
	}
}