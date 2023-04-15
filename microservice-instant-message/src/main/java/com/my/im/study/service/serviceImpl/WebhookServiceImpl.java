package com.my.im.study.service.serviceImpl;

import com.my.im.study.apibody.EventBean;
import com.my.im.study.database.GroupService;
import com.my.im.study.database.MemberService;
import com.my.im.study.database.entity.Group;
import com.my.im.study.service.WebhookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class WebhookServiceImpl implements WebhookService {
    private static final RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private GroupService groupService;
    @Autowired
    private MemberService memberService;
    @Override
    public String setWebhook(String groupId,String webhook) {
        return groupService.setWebhook(groupId,webhook);
    }

    @Override
    public String testWebhook(String groupId) {
        return webhookSendEvent(groupId,EventBean.createTestEventBean());
    }

    @Override
    public String webhookSendEvent(String groupId,EventBean eventBean) {
        try {
            restTemplate.postForObject(groupService.getWebhook(groupId), eventBean,String.class);
        }catch (Exception e){
            System.err.printf("Webhook error: can't send event to %s, with exception:%s,print by %s\n",groupService.getWebhook(groupId),e.getMessage(),this.getClass());
            return e.getMessage();
        }
        return "Success!";
    }
    @Override
    public void webhookSendEvent(String instantMessagingSoftware, String instantMessagingSoftwareUserId, EventBean eventBean) {
        if(eventBean.getEventType().equals("Transfer")) return;
        List<Group> groups= memberService.getGroups(instantMessagingSoftware,instantMessagingSoftwareUserId);
        for(Group group:groups){
            webhookSendEvent(group.getGroupId(),eventBean);
        }
    }
}