package com.my.im.study.webhook;

import com.my.im.study.apibody.EventBody;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebhookServiceImpl  implements WebhookService{
    private boolean webhookEnable;
    private RestTemplate restTemplate;
    private String webhookURL;

    public WebhookServiceImpl(){
        webhookURL = System.getenv("");
        restTemplate = new RestTemplate();
        testWebhook();
    }

    @Override
    public void setWebhook(String webhookURL) {

    }

    @Override
    public boolean testWebhook() {
        webhookSendEvent(new EventBody("Webhook test",""));
        return false;
    }

    @Override
    public void webhookSendEvent(EventBody eventBody) {
        restTemplate.postForObject(webhookURL,EventBody.class,null);
    }
}
