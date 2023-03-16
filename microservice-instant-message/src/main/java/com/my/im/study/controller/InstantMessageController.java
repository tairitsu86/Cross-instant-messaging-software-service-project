package com.my.im.study.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.my.im.study.linebot.LineMessageService;
import com.my.im.study.telegrambot.TelegramMessageService;

@RestController
public class InstantMessageController {
	
	@Autowired
    private LineMessageService lineMessageService;
	@Autowired
	private TelegramMessageService telegramMessageService;
	
	@GetMapping("/")
	public String home() {
		return "{\"Message\":\"OAO\"}";
	}
	
	//http://localhost:8080/linebot/send/U577163e408ae2140e205910efd46f143/OWO
	@GetMapping("/linebot/send/{userId}/{message}")
	public String linebotSendText(@PathVariable String userId,@PathVariable String message) {
		lineMessageService.pushTextMessage(userId, message);
		return "{\"Message\":\"OWO\"}";
	}
	
	//http://localhost:8080/telegrambot/send/6200393693/OWO
	@GetMapping("/telegrambot/send/{chatId}/{message}")
	public String telegrambotSendText(@PathVariable Long chatId,@PathVariable String message) {
		telegramMessageService.sendTextMessage(chatId, message);
		return "{\"Message\":\"OWO\"}";
	}

	
	
	
	
	
}