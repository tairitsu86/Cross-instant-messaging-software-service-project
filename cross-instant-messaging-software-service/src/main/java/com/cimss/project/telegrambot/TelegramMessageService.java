package com.cimss.project.telegrambot;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

@Service
public class TelegramMessageService {
	
	@Autowired
	private TelegramBot telegramBot;
	
	SendMessage request;
	
	public void sendTextMessage(Long chatId, String message) {
		request = new SendMessage(chatId, message)
				.parseMode(ParseMode.HTML)
				.disableWebPagePreview(false)
		        .disableNotification(false);
		System.out.println(request.toWebhookResponse());
		telegramBot.execute(request, new Callback<SendMessage, SendResponse>() {
			@Override
			public void onResponse(SendMessage request, SendResponse response) {
				System.out.println(response);
			}
			@Override
			public void onFailure(SendMessage request, IOException e) {
				e.printStackTrace();
			}
		});
	}
}
