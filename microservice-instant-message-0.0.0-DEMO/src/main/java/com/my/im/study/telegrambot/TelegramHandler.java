package com.my.im.study.telegrambot;

import com.pengrad.telegrambot.model.Update;

public interface TelegramHandler {

	void handleUpdate(Update update);
}
