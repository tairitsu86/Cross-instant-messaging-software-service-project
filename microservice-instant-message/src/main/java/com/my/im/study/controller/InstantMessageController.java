package com.my.im.study.controller;

import com.my.im.study.apibody.*;
import com.my.im.study.database.entity.Group;
import com.my.im.study.service.CrossPlatformService;
import com.my.im.study.service.WebhookService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class InstantMessageController {
	
	@Autowired
    private CrossPlatformService crossPlatformService;

	@Autowired
	private WebhookService webhookService;

	//get method

	@Operation(summary = "Home test")
	@GetMapping("/")
	public ManageBean home() {
		return new ManageBean("OAO");
	}

	@GetMapping("/webhooktest")
	public void webhookTest(@RequestHeader(name = "Authorization") String accessToken,
							@RequestHeader(name = "InstantMessagingSoftware",required = false) String instantMessagingSoftware,
							@RequestParam String groupId){
		webhookService.testWebhook(groupId);
	}

	@GetMapping("/setwebhook")
	public void setWebhook(@RequestHeader(name = "Authorization") String accessToken,
						   @RequestHeader(name = "InstantMessagingSoftware",required = false) String instantMessagingSoftware,
						   @RequestParam String groupId,
						   @RequestParam String webhook){
		webhookService.setWebhook(groupId,webhook);
	}

	//post method
	@Operation(summary = "Send text message")
	@PostMapping("/send")
	public ManageBean sendTextMessage(@RequestHeader(name = "Authorization") String accessToken,
                                      @RequestHeader(name = "InstantMessagingSoftware",required = false) String instantMessagingSoftware,
                                      @RequestBody ManageBean manageBean) {
		System.out.println(manageBean);
		return new ManageBean(crossPlatformService.sendTextMessage(manageBean.getInstantMessagingSoftware(), manageBean.getInstantMessagingSoftwareUserId(), manageBean.getMessage()));
	}
	@PostMapping("/broadcast")
	public ManageBean broadcast(@RequestHeader(name = "Authorization") String accessToken,
                                @RequestHeader(name = "InstantMessagingSoftware",required = false) String instantMessagingSoftware,
                                @RequestBody ManageBean manageBean) {
		System.out.println(manageBean);
		return new ManageBean(crossPlatformService.broadcast(manageBean.getGroupId(), manageBean.getMessage()));
	}
	@PostMapping("/newgroup")
	public Group newGroup(@RequestHeader(name = "Authorization") String accessToken,
						  @RequestHeader(name = "InstantMessagingSoftware",required = false) String instantMessagingSoftware,
						  @RequestBody ManageBean manageBean) {
		System.out.println(manageBean);
		return crossPlatformService.newGroup(manageBean.getGroupName(),manageBean.getGroupWebhook());
	}

	@PostMapping("/join")
	public ManageBean join(@RequestHeader(name = "Authorization") String accessToken,
                           @RequestHeader(name = "InstantMessagingSoftware",required = false) String instantMessagingSoftware,
                           @RequestBody ManageBean manageBean) {
		System.out.println(manageBean);
		return new ManageBean(crossPlatformService.join(manageBean.getInstantMessagingSoftware(), manageBean.getInstantMessagingSoftwareUserId(), manageBean.getGroupId()));
	}


}