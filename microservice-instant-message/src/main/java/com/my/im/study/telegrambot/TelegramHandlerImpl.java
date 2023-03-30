package com.my.im.study.telegrambot;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.im.study.apibody.EventBean;
import com.my.im.study.service.CrossPlatformService;
import com.my.im.study.service.InstantMessagingSoftwareList;
import com.my.im.study.service.WebhookService;
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
	@Autowired
	private WebhookService webhookService;
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public void handleUpdate(Update update) {
		if(update.message()==null) return;
		Message message = update.message();
		String text = message.text();
		Long chatId = message.chat().id();
		LOG.debug("Chat id:" + chatId);
		LOG.debug("Text : " + text);
		webhookService.webhookSendEvent(InstantMessagingSoftwareList.TELEGRAM.getName()
				,chatId.toString()
				,EventBean.createTextMessageEventBean(InstantMessagingSoftwareList.TELEGRAM.getName(),chatId.toString(),text));
		try {
			webhookService.webhookSendEvent(InstantMessagingSoftwareList.TELEGRAM.getName()
					,chatId.toString()
					,EventBean.createTransferEventBean(InstantMessagingSoftwareList.TELEGRAM.getName()
							,objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY).writeValueAsString(update)));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		crossPlatformService.userRegister(InstantMessagingSoftwareList.TELEGRAM.getName(),chatId.toString(),message.chat().lastName()+message.chat().firstName());
	}

}