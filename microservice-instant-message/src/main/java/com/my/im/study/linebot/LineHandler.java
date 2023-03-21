package com.my.im.study.linebot;

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
import com.my.im.study.service.CommandEvent;
import com.my.im.study.service.InstantMessagingSoftwareList;

@LineMessageHandler
public class LineHandler {
	
    private final Logger log = LoggerFactory.getLogger(InstantMessageApplication.class);
    
    @Autowired
    private CommandEvent commandEvent;
    
    @EventMapping
    public Message handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        log.info("event: " + event);
        final String text = event.getMessage().getText();
//        if(text.startsWith("/")) {
//        	String commandResult = commandEvent.handleCommandEvent(event,InstantMessagingSoftwareList.LINE);
//        	return new TextMessage(commandResult);
//        }
        return new TextMessage(text);
    }

    @EventMapping
    public void handleDefaultMessageEvent(Event event) {
        System.out.println("event: " + event);
    }
    
    
    
    
}
