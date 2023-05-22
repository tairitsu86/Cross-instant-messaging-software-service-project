package com.cimss.project.service.serviceImpl;

import com.cimss.project.database.DatabaseService;
import com.cimss.project.database.entity.Group;
import com.cimss.project.database.entity.token.DeliveryMode;
import com.cimss.project.service.CIMSService;
import com.cimss.project.service.NotifyService;
import com.cimss.project.apibody.EventBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;

@Service
public class NotifyServiceImpl implements NotifyService {
    private static final RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private CIMSService cimsService;
    @Autowired
    private DatabaseService databaseService;

    @Override
    public void notifyTest(String groupId) {
        notify(groupId,EventBean.createTestEventBean());
    }

    @Override
    public EventBean.ReplyEvent notify(String groupId, EventBean event) {
        Group group = databaseService.getGroupById(groupId);
        Set<DeliveryMode> deliveryModes = group.getDeliveryMode();
        EventBean.ReplyEvent replyEvent = null;
        for(DeliveryMode deliveryMode:deliveryModes){
            switch (deliveryMode){
                case WEBHOOK-> replyEvent = webhookSendEvent(group.getDeliveryConfig().getWebhookConfig(),event);
                case RABBITMQ-> replyEvent = rabbitMQSendEvent(group.getDeliveryConfig().getRabbitMQConfig(),event);
            }
        }
        return replyEvent;
    }

    public EventBean.ReplyEvent webhookSendEvent(DeliveryMode.DeliveryConfig.WebhookConfig webhookConfig, EventBean event) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        if(webhookConfig.getHeader()!=null)
            webhookConfig.getHeader().forEach((key, value) -> headers.add(key, value));
        HttpEntity<EventBean> httpEntity = new HttpEntity<>(event,headers);
        ResponseEntity<EventBean.ReplyEvent> replyEvent = null;
        try {
            replyEvent = restTemplate.exchange(webhookConfig.getUrl(), HttpMethod.POST, httpEntity, EventBean.ReplyEvent.class);
        }catch (Exception e){
            System.err.printf("Webhook error: can't send event to %s, with exception:%s,print by %s\n",webhookConfig.getUrl(),e.getMessage(),this.getClass());
        }
        return replyEvent==null?null:replyEvent.getBody();
    }
    public EventBean.ReplyEvent rabbitMQSendEvent(DeliveryMode.DeliveryConfig.RabbitMQConfig rabbitMQConfig, EventBean event) {
        //TODO
        return null;
    }


}
