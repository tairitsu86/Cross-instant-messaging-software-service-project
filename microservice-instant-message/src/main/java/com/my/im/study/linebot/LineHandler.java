package com.my.im.study.linebot;

import com.my.im.study.apibody.EventBean;
import com.my.im.study.database.entity.User;
import com.my.im.study.service.CrossPlatformService;
import com.my.im.study.service.InstantMessagingSoftwareList;
import com.my.im.study.service.WebhookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import com.my.im.study.InstantMessageApplication;

@LineMessageHandler
public class LineHandler {
	
    private final Logger log = LoggerFactory.getLogger(InstantMessageApplication.class);
    
    @Autowired
    private CrossPlatformService crossPlatformService;

    @Autowired
    private LineMessageService lineMessageService;

    @Autowired
    private WebhookService webhookService;

    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        log.info("event: " + event);
        final String text = event.getMessage().getText();
        String userId = event.getSource().getUserId();
        webhookService.webhookTransferEvent(InstantMessagingSoftwareList.LINE.getName(),userId,new EventBean(InstantMessagingSoftwareList.LINE.getName(),event));
        crossPlatformService.userRegister(InstantMessagingSoftwareList.LINE.getName(),userId,lineMessageService.getUserProfile(userId).getDisplayName());

    }

    @EventMapping
    public void handleDefaultMessageEvent(Event event) {
        System.out.println("event: " + event);
    }
    
    
    
    
}
