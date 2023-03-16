package com.my.im.study.telegrambot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pengrad.telegrambot.TelegramBot;

@Configuration
public class TelegramBotBean {
	private final String BOT_TOKEN = "";
	@Bean
	public TelegramBot getTelegramBot() {
		return new TelegramBot(BOT_TOKEN);
	}
}
