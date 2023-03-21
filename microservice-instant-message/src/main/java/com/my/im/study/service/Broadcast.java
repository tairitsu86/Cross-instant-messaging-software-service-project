package com.my.im.study.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.im.study.database.MemberService;
import com.my.im.study.database.entity.User;
import com.my.im.study.linebot.LineMessageService;
import com.my.im.study.telegrambot.TelegramMessageService;

@Service
public class Broadcast {
	@Autowired
    private MemberService memberService;
	@Autowired
	private LineMessageService lineMessageService;
	@Autowired
	private TelegramMessageService telegramMessageService;
	
	public void broadcast(String groupId,String text) {
		List<User> users = memberService.getUsers(groupId);
		for(User user:users) {
			switch(InstantMessagingSoftwareList.valueOf(user.getInstantMessagingSoftware())) {
			case LINE:
				lineMessageService.pushTextMessage(user.getInstantMessagingSoftwareUserId(),text);
				break;
			case TELEGRAM:
				telegramMessageService.sendTextMessage(Long.parseLong(user.getInstantMessagingSoftwareUserId()), text);
				break;
			}
		}
	}
}
