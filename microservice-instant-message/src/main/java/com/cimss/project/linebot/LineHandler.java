package com.cimss.project.linebot;

import com.cimss.project.InstantMessageApplication;
import com.cimss.project.service.WebhookService;
import com.cimss.project.service.CrossIMSService;
import com.cimss.project.service.InstantMessagingSoftwareList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

@LineMessageHandler
public class LineHandler {
	
    private final Logger log = LoggerFactory.getLogger(InstantMessageApplication.class);
    
    @Autowired
    private CrossIMSService crossIMSService;

    @Autowired
    private LineMessageService lineMessageService;

    @Autowired
    private WebhookService webhookService;


    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        log.info("event: " + event);
        final String text = event.getMessage().getText();
        String userId = event.getSource().getUserId();
        crossIMSService.IMSWebhookTextEventHandler(InstantMessagingSoftwareList.LINE.getName(),userId,text);
//        webhookService.webhookSendEvent(InstantMessagingSoftwareList.LINE.getName(),userId,EventBean.createTransferEventBean(InstantMessagingSoftwareList.LINE.getName(),event));
        crossIMSService.userRegister(InstantMessagingSoftwareList.LINE.getName(),userId,lineMessageService.getUserProfile(userId).getDisplayName());
    }

    @EventMapping
    public void handleDefaultMessageEvent(Event event) {
        System.out.println("event: " + event);
    }

}
