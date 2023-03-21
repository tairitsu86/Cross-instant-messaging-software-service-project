package com.my.im.study.telegrambot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pengrad.telegrambot.TelegramBot;

@Configuration
public class TelegramBotBean {
	
	
	@Bean
	public TelegramBot getTelegramBot() {
		return new TelegramBot(System.getenv("TELEGRAM_BOT_TOKEN"));
	}
}
