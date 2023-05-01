package com.cimss.project.service;

import com.cimss.project.database.entity.UserId;

public interface EventHandleService {
    void TextEventHandler(UserId userId, String text);
}
