package com.cimss.project.service;

import com.cimss.project.apibody.EventBean;
import com.cimss.project.database.entity.UserId;

public interface NotifyService {
    void testWebhook(String groupId);
    void webhookSendEvent(String groupId,EventBean eventBean);
    void webhookSendEvent(UserId userId, EventBean eventBean);
    void notify(String groupId,String message);
}
