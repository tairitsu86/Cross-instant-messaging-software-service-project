package com.cimss.project.service;

import com.cimss.project.database.entity.UserId;

public interface EventHandleService {
    void textEventHandler(UserId userId, String text);
    void commandEventHandler(UserId userId, String text);
}
