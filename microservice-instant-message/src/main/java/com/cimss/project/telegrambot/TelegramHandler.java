package com.cimss.project.telegrambot;

import com.pengrad.telegrambot.model.Update;

public interface TelegramHandler {

	void handleUpdate(Update update);
}
