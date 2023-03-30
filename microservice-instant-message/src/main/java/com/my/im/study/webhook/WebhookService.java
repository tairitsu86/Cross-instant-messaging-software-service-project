package com.my.im.study.webhook;

import com.my.im.study.apibody.EventBody;

public interface WebhookService {
    void setWebhook(String webhookURL);
    boolean testWebhook();
    void webhookSendEvent(EventBody eventBody);
}
