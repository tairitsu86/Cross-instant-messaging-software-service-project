package com.cimss.project.im.linebot;

import com.cimss.project.CrossIMApplication;
import com.cimss.project.database.entity.UserId;
import com.cimss.project.service.EventHandleService;
import com.cimss.project.service.CIMSService;
import com.cimss.project.service.token.InstantMessagingSoftwareList;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.profile.UserProfileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.util.concurrent.ExecutionException;

@LineMessageHandler
public class LineHandler {
	
    private final Logger log = LoggerFactory.getLogger(CrossIMApplication.class);
    
    @Autowired
    private CIMSService cimsService;
    @Autowired
    private EventHandleService eventHandleService;
    @Autowired
    private LineMessagingClient lineMessagingClient;


    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        log.info("event: " + event);
        final String text = event.getMessage().getText();
        String userId = event.getSource().getUserId();
        cimsService.userRegister(UserId.CreateUserId(InstantMessagingSoftwareList.LINE.name(),userId),getUserProfile(userId).getDisplayName());
        eventHandleService.TextEventHandler(UserId.CreateUserId(InstantMessagingSoftwareList.LINE.name(),userId),text);
    }

    @EventMapping
    public void handleDefaultMessageEvent(Event event) {
        System.out.println("event: " + event);
    }
    public UserProfileResponse getUserProfile(String userId){
        UserProfileResponse userProfileResponse = null;
        try {
            userProfileResponse = lineMessagingClient.getProfile(userId).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        if(userProfileResponse==null) return null;
        System.out.println(userProfileResponse.getDisplayName());
        return userProfileResponse;
    }
}
