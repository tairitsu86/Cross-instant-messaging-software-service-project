package com.cimss.project.im.linebot;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.cimss.project.im.ButtonList;
import com.cimss.project.im.IMService;
import com.linecorp.bot.model.action.Action;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.template.ButtonsTemplate;
import com.linecorp.bot.model.message.template.ConfirmTemplate;
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

	public void pushMessage(String userId, Message message){
		PushMessage pushMessage = new PushMessage(userId, message);
		System.out.println(pushMessage.getTo()+"\n"+pushMessage.getMessages());
		BotApiResponse response = null;
		try {
			response = lineMessagingClient.pushMessage(pushMessage).get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
    @Override
	public void sendTextMessage(String userId, String textMessage){
		TextMessage message = new TextMessage(textMessage);
		pushMessage(userId,message);
	}

	@Override
	public void sendButtonListMessage(String userId, ButtonList buttonList) {
		List<Action> actions = new ArrayList<>();
		buttonList.getButtons().forEach((key,value)->actions.add(new PostbackAction(key, value,null,null,null,null)));
		String text = buttonList.getText();
		TemplateMessage templateMessage = new TemplateMessage(
				"Line button list",
				ButtonsTemplate.builder()
						.title(buttonList.getTitle())
						.text(text.substring(0,Math.min(text.length(),60)))
						.actions(actions).build()
		);
		pushMessage(userId,templateMessage);
	}

}
