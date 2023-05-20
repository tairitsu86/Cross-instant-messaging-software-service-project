package com.cimss.project.controller;

import com.cimss.project.apibody.ManageBean;
import com.cimss.project.database.entity.Group;
import com.cimss.project.database.entity.Member;
import com.cimss.project.service.APIHandlerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Group manager Services API")
@RestController
public class ManagerController {

	@Autowired
	private APIHandlerService apiHandlerService;

	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Get data of all members", description = "Can get name,user id,the IM he/she used, and member kind.")
	@GetMapping("/groups/{groupId}/members")
	public List<Member.MemberData> getMembers(@RequestHeader(name = "Authorization") String accessToken,
											  @Parameter(description = "The id of the group, composed of six alphanumerics.", example = "AbCd12")
											  @PathVariable String groupId){
		return apiHandlerService.getMembers(accessToken,groupId);
	}

	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "The detail data of the group", description = "Get the group data in detail by group id.")
	@GetMapping("/groups/{groupId}/detail")
	public Group groupDetail(@RequestHeader(name = "Authorization") String accessToken,
							 @Parameter(description = "The id of the group, composed of six alphanumerics.", example = "AbCd12")
							 @PathVariable String groupId) {
		return apiHandlerService.groupDetail(accessToken,groupId);
	}

	//post method
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Webhook test", description = "Send a test event to the webhook of the group.")
	@PostMapping("/groups/{groupId}/webhook/test")
	public void webhookTest(@RequestHeader(name = "Authorization") String accessToken,
							@Parameter(description = "The id of the group, composed of six alphanumerics.", example = "AbCd12")
							@PathVariable String groupId){
		apiHandlerService.webhookTest(accessToken,groupId);
	}

	//Put method
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Broadcast text message", description = "Broadcast text message to everyone in the group.")
	@PutMapping("/broadcast/text")
	public void broadcast(@RequestHeader(name = "Authorization") String accessToken,
						  @Valid @RequestBody ManageBean.BroadcastBean broadcastBean) {
		apiHandlerService.broadcast(accessToken,broadcastBean);
	}


	//Patch method
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Alter the group attributes", description = "Request body must have groupId.")
	@PatchMapping("/groups/alter")
	public void alterGroup(@RequestHeader(name = "Authorization") String accessToken,
						   @Valid @RequestBody ManageBean.AlterGroupBean alterGroupBean) {
		apiHandlerService.alterGroup(accessToken,alterGroupBean);
	}
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Alter the group function list", description = "Request body must have groupId.")
	@PatchMapping("/groups/alter/list")
	public void alterList(@RequestHeader(name = "Authorization") String accessToken,
						  @Valid @RequestBody ManageBean.FunctionListBean functionListBean) {
		apiHandlerService.alterList(accessToken,functionListBean);
	}
}