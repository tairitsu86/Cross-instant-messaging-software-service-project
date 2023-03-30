package com.my.im.study.service;

import com.my.im.study.apibody.EventBean;
import com.my.im.study.database.GroupService;
import com.my.im.study.database.MemberService;
import com.my.im.study.database.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class WebhookServiceImpl  implements WebhookService{
    private static RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private GroupService groupService;
    @Autowired
    private MemberService memberService;
    @Override
    public void setWebhook(String groupId,String webhook) {
        groupService.setWebhook(groupId,webhook);
    }

    @Override
    public void testWebhook(String groupId) {
        webhookSendEvent(groupService.getWebhook(groupId),new EventBean("Webhook test",""));
    }

    @Override
    public void webhookSendEvent(String groupId,EventBean eventBean) {
        restTemplate.postForObject(groupService.getWebhook(groupId), EventBean.class,EventBean.class);
    }

    @Override
    public void webhookTransferEvent(String instantMessagingSoftware,String instantMessagingSoftwareUserId, EventBean eventBean) {
        List<Group> groups= memberService.getGroups(instantMessagingSoftware,instantMessagingSoftwareUserId);
        for(Group group:groups){
            webhookSendEvent(group.getGroupId(),eventBean);
        }
    }
}
