package com.cimss.project.im.telegrambot;

import com.cimss.project.database.entity.UserId;
import com.cimss.project.service.CIMSService;
import com.cimss.project.service.EventHandleService;
import com.cimss.project.service.token.InstantMessagingSoftwareList;
import com.fasterxml.jackson.databind.ObjectMapper;
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
	private CIMSService cimsService;

	@Autowired
	private EventHandleService eventHandleService;

	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public void handleUpdate(Update update) {
		if(update.message()==null) return;
		Message message = update.message();
		String text = message.text();
		Long chatId = message.chat().id();
		LOG.debug("Chat id:" + chatId);
		LOG.debug("Text : " + text);
		cimsService.userRegister(UserId.CreateUserId(InstantMessagingSoftwareList.TELEGRAM.name(),chatId.toString()),message.chat().lastName()+message.chat().firstName());
		eventHandleService.TextEventHandler(UserId.CreateUserId(InstantMessagingSoftwareList.TELEGRAM.name(),chatId.toString()),text);
	}

}