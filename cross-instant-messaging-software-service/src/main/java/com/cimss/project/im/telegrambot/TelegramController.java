package com.cimss.project.im.telegrambot;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pengrad.telegrambot.BotUtils;

@Hidden
@RestController
public class TelegramController {
	
	@Autowired
	private TelegramHandler telegramHandler;

	@PostMapping("${TELEGRAM_WEBHOOK}")
	public void tgWebhook(@RequestBody String update) {
		System.out.println(update);
		telegramHandler.handleUpdate(BotUtils.parseUpdate(update));
	}
}
