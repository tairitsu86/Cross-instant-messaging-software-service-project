package com.my.im.study.linebot;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.profile.UserProfileResponse;
import com.linecorp.bot.model.response.BotApiResponse;

@Service
public class LineMessageService {
	
	@Autowired
	private LineMessagingClient lineMessagingClient;
    
	public void pushTextMessage(String userId, String messageText){
		TextMessage message = new TextMessage(messageText);
		PushMessage pushMessage = new PushMessage(userId, message);
		System.out.println(pushMessage.getTo()+"\n"+pushMessage.getMessages());
        BotApiResponse response = null;
		try {
			response = lineMessagingClient.pushMessage(pushMessage).get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
        System.out.println(response);
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
