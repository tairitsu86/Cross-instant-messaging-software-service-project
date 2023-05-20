package com.cimss.project.im.telegrambot;

import java.io.IOException;

import com.cimss.project.im.ButtonList;
import com.cimss.project.im.IMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

@Service("TELEGRAM")
public class TelegramMessageService implements IMService {
	
	@Autowired
	private TelegramBot telegramBot;

	@Override
	public void sendTextMessage(String userId, String textMessage) {
		long chatId = Long.parseLong(userId);
		SendMessage request = new SendMessage(chatId, textMessage)
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

	@Override
	public void sendButtonListMessage(String userId,ButtonList buttonList) {

	}
}
