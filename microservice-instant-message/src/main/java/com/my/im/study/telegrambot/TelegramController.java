package com.my.im.study.telegrambot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pengrad.telegrambot.BotUtils;

@RestController
public class TelegramController {
	
	@Autowired
	private TelegramHandler handler;
	
	@PostMapping("/telegramWebhook")
	public void tgWebhook(@RequestBody String update) {
		handler.handleUpdate(BotUtils.parseUpdate(update));
	}
}
