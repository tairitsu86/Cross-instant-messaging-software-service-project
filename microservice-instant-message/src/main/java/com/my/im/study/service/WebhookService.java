package com.my.im.study.service;

import com.my.im.study.apibody.EventBean;

public interface WebhookService {
    void setWebhook(String groupId,String webhook);
    void testWebhook(String groupId);
    void webhookSendEvent(String groupId,EventBean eventBean);

    void webhookTransferEvent(String instantMessagingSoftware,String instantMessagingSoftwareUserId,EventBean eventBean);
}
