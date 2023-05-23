package com.cimss.project.im.telegrambot;

import com.cimss.project.database.entity.UserId;
import com.cimss.project.handler.EventHandler;
import com.cimss.project.database.entity.token.InstantMessagingSoftware;
import com.pengrad.telegrambot.model.CallbackQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;

@Component
public class TelegramHandlerImpl implements TelegramHandler {

	private Logger LOG = LoggerFactory.getLogger(TelegramHandlerImpl.class);

	@Autowired
	private EventHandler eventHandler;
	
	@Override
	public void handleUpdate(Update update) {
		System.out.println(update);
		if(update.message()!=null)
			messageUpdateHandler(update.message());
		else if(update.callbackQuery()!=null)
			inlineButtonUpdateHandler(update.callbackQuery());

	}
	public void messageUpdateHandler(Message message){
		String text = message.text();
		Long chatId = message.chat().id();
		LOG.debug("Chat id:" + chatId);
		LOG.debug("Text : " + text);
		eventHandler.textEventHandler(UserId.CreateUserId(InstantMessagingSoftware.TELEGRAM,String.valueOf(chatId)),text);
	}
	public void inlineButtonUpdateHandler(CallbackQuery callbackQuery){
		String data = callbackQuery.data();
		Long chatId = callbackQuery.from().id();
		LOG.debug("Chat id:" + chatId);
		LOG.debug("Text : " + data);
		eventHandler.textEventHandler(UserId.CreateUserId(InstantMessagingSoftware.TELEGRAM,String.valueOf(chatId)),data);
	}

}