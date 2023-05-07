package com.cimss.project.im.linebot;

import java.util.concurrent.ExecutionException;

import com.cimss.project.im.IMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;

@Service("LINE")
public class LineMessageService implements IMService {
	
	@Autowired
	private LineMessagingClient lineMessagingClient;
    @Override
	public String sendTextMessage(String userId, String textMessage){
		TextMessage message = new TextMessage(textMessage);
		PushMessage pushMessage = new PushMessage(userId, message);
		System.out.println(pushMessage.getTo()+"\n"+pushMessage.getMessages());
        BotApiResponse response = null;
		try {
			response = lineMessagingClient.pushMessage(pushMessage).get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
        return null;
	}

}
