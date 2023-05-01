package com.cimss.project.service;

import com.cimss.project.apibody.EventBean;
import com.cimss.project.database.entity.UserId;

public interface WebhookService {
    String testWebhook(String groupId);
    String webhookSendEvent(String groupId,EventBean eventBean);
    void webhookSendEvent(UserId userId, EventBean eventBean);
}
