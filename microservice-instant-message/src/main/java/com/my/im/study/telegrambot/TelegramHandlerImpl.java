package com.my.im.study.telegrambot;

import com.my.im.study.service.CrossPlatformService;
import com.my.im.study.service.InstantMessagingSoftwareList;
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
	private CrossPlatformService crossPlatformService;
	
	@Override
	public void handleUpdate(Update update) {
		if(update.message()==null) return;
		Message message = update.message();
		String text = message.text();
		Long chatId = message.chat().id();
		LOG.debug("Chat id:" + chatId);
		LOG.debug("Text : " + text);
		crossPlatformService.userRegister(InstantMessagingSoftwareList.TELEGRAM.getName(),chatId.toString(),message.chat().lastName()+message.chat().firstName());
	}

}