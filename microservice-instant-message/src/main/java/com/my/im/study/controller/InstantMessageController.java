package com.my.im.study.controller;

import com.my.im.study.APIBody.*;
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
	public MessageBody home() {
		return new MessageBody("OAO");
	}

	@Operation(summary = "Send text message")
	@PostMapping("/send")
	public MessageBody sendTextMessage(@RequestHeader(name = "Authorization") String accessToken,
									   @RequestHeader(name = "InstantMessagingSoftware",required = false) String instantMessagingSoftware,
									   @RequestBody MessageBody messageBody) {
		System.out.println(messageBody);
		return new MessageBody(crossPlatformService.sendTextMessage(messageBody.getInstantMessagingSoftware(),messageBody.getInstantMessagingSoftwareUserId(),messageBody.getMessage()));
	}
	@PostMapping("/broadcast")
	public MessageBody broadcast(@RequestHeader(name = "Authorization") String accessToken,
									   @RequestHeader(name = "InstantMessagingSoftware",required = false) String instantMessagingSoftware,
									   @RequestBody MessageBody messageBody) {
		System.out.println(messageBody);
		return new MessageBody(crossPlatformService.broadcast(messageBody.getGroupId(),messageBody.getMessage()));
	}

}