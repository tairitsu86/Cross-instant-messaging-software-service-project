package com.my.im.study.telegrambot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.my.im.study.service.CommandEvent;
import com.my.im.study.service.InstantMessagingSoftwareList;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;

@Component
@ComponentScan
public class TelegramHandlerImpl implements TelegramHandler {

	private Logger LOG = LoggerFactory.getLogger(TelegramHandlerImpl.class);
	
	@Autowired
	private TelegramMessageService telegramMessageService;
	
	@Autowired
	private CommandEvent commandEvent;
	
	@Override
	public void handleUpdate(Update update) {
		if(update.message()==null) return;
		Message message = update.message();
		String text = message.text();
		Long chatId = message.chat().id();
		LOG.debug("Chat id:" + chatId);
		LOG.debug("Text : " + text);
		
		if(text.startsWith("/")) {
			String commandResult = commandEvent.handleCommandEvent(message, InstantMessagingSoftwareList.TELEGRAM);
			telegramMessageService.sendTextMessage(chatId, commandResult);
			return;
		}
		telegramMessageService.sendTextMessage(chatId, text);
	}

}