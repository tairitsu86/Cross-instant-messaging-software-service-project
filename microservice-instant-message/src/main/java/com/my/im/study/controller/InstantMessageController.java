package com.my.im.study.controller;

import com.linecorp.bot.model.response.BotApiResponse;
import com.my.im.study.APIBody.*;
import com.my.im.study.service.CrossPlatformService;
import com.pengrad.telegrambot.response.SendResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.my.im.study.linebot.LineMessageService;
import com.my.im.study.telegrambot.TelegramMessageService;

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
									   @RequestHeader(name = "Platform",required = false) String platform,
									   @RequestBody MessageBody messageBody) {
		System.out.println(messageBody);
		return new MessageBody(crossPlatformService.sendTextMessage(messageBody.getPlatform(),messageBody.getInstantMessagingSoftwareUserId(),messageBody.getMessage()));
	}
	@PostMapping("/broadcast")
	public MessageBody broadcast(@RequestHeader(name = "Authorization") String accessToken,
									   @RequestHeader(name = "Platform",required = false) String platform,
									   @RequestBody MessageBody messageBody) {
		System.out.println(messageBody);
		return new MessageBody(crossPlatformService.broadcast(null,null,messageBody.getGroupId(),messageBody.getMessage()));
	}

	
	
	
	
}