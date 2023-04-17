package com.cimss.project.service;

import com.cimss.project.apibody.EventBean;

public interface WebhookService {
    String setWebhook(String groupId,String webhook);
    String testWebhook(String groupId);
    String webhookSendEvent(String groupId,EventBean eventBean);
    void webhookSendEvent(String instantMessagingSoftware, String instantMessagingSoftwareUserId, EventBean eventBean);
}
