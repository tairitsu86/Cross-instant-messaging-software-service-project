package com.cimss.project.im.telegrambot;

import com.pengrad.telegrambot.model.Update;

public interface TelegramHandler {

	void handleUpdate(Update update);
}
