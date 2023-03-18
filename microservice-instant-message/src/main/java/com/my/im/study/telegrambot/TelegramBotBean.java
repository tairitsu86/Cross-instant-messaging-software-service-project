package com.my.im.study.telegrambot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pengrad.telegrambot.TelegramBot;

@Configuration
public class TelegramBotBean {
	private final String BOT_TOKEN = "6016022424:AAGw6pyWTq6wZb4OeJiTwvpKsJ5fTbEnLZo";
	@Bean
	public TelegramBot getTelegramBot() {
		return new TelegramBot(BOT_TOKEN);
	}
}
