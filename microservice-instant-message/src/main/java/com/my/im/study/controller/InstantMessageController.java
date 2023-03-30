package com.my.im.study.controller;

import com.my.im.study.apibody.*;
import com.my.im.study.database.entity.Group;
import com.my.im.study.service.CrossPlatformService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class InstantMessageController {
	
	@Autowired
    private CrossPlatformService crossPlatformService;

	@Operation(summary = "Home test")
	@GetMapping("/")
	public ManageBody home() {
		return new ManageBody("OAO");
	}

	@Operation(summary = "Send text message")
	@PostMapping("/send")
	public ManageBody sendTextMessage(@RequestHeader(name = "Authorization") String accessToken,
									   @RequestHeader(name = "InstantMessagingSoftware",required = false) String instantMessagingSoftware,
									   @RequestBody ManageBody manageBody) {
		System.out.println(manageBody);
		return new ManageBody(crossPlatformService.sendTextMessage(manageBody.getInstantMessagingSoftware(),manageBody.getInstantMessagingSoftwareUserId(),manageBody.getMessage()));
	}
	@PostMapping("/broadcast")
	public ManageBody broadcast(@RequestHeader(name = "Authorization") String accessToken,
									   @RequestHeader(name = "InstantMessagingSoftware",required = false) String instantMessagingSoftware,
									   @RequestBody ManageBody manageBody) {
		System.out.println(manageBody);
		return new ManageBody(crossPlatformService.broadcast(manageBody.getGroupId(),manageBody.getMessage()));
	}
	@PostMapping("/newgroup")
	public Group newGroup(@RequestHeader(name = "Authorization") String accessToken,
						  @RequestHeader(name = "InstantMessagingSoftware",required = false) String instantMessagingSoftware,
						  @RequestBody ManageBody manageBody) {
		System.out.println(manageBody);
		return crossPlatformService.newGroup(manageBody.getGroupName());
	}

	@PostMapping("/join")
	public ManageBody join(@RequestHeader(name = "Authorization") String accessToken,
							@RequestHeader(name = "InstantMessagingSoftware",required = false) String instantMessagingSoftware,
							@RequestBody ManageBody manageBody) {
		System.out.println(manageBody);
		return new ManageBody(crossPlatformService.join(manageBody.getInstantMessagingSoftware(),manageBody.getInstantMessagingSoftwareUserId(),manageBody.getGroupId()));
	}
}