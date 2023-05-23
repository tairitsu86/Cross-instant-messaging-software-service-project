package com.cimss.project.im.telegrambot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cimss.project.im.ButtonList;
import com.cimss.project.im.IMService;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

@Service("TELEGRAM")
public class TelegramService implements IMService {
	
	@Autowired
	private TelegramBot telegramBot;

	@Override
	public void sendTextMessage(String userId, String textMessage) {
		long chatId = Long.parseLong(userId);
		sendMessage(new SendMessage(chatId, textMessage)
				.parseMode(ParseMode.HTML)
				.disableWebPagePreview(false)
				.disableNotification(false)
		);
	}

	@Override
	public void sendButtonListMessage(String userId,ButtonList buttonList) {
		long chatId = Long.parseLong(userId);
		List<InlineKeyboardButton[]> buttons = new ArrayList<>();
		buttonList.getButtons().forEach((key,value)->buttons.add(new InlineKeyboardButton[]{new InlineKeyboardButton(key).callbackData(value)}));
		sendMessage(new SendMessage(chatId, String.format("%s\n\n%s",buttonList.getTitle(),buttonList.getText()))
				.parseMode(ParseMode.HTML)
				.disableWebPagePreview(false)
				.disableNotification(false)
				.replyMarkup(new InlineKeyboardMarkup(buttons.toArray(new InlineKeyboardButton[buttons.size()][])))
		);
	}
	public void sendMessage(SendMessage request){
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
