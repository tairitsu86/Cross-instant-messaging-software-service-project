package com.my.im.study.telegrambot;

import com.pengrad.telegrambot.model.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pengrad.telegrambot.BotUtils;

@RestController
public class TelegramController {
	
	@Autowired
	private TelegramHandler handler;

	//telegram's update object should map the value from request body, but it didn't, i don't know why
	//maybe because spring not support telegram package?
	@PostMapping("${TELEGRAM_WEBHOOK}")
	public void tgWebhook(@RequestBody String update) {
		System.out.println(update);
		handler.handleUpdate(BotUtils.parseUpdate(update));
	}
}
