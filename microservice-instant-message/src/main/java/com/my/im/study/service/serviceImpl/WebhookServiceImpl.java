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
        System.out.println(groupService.getWebhook(groupId));
        webhookSendEvent(groupService.getWebhook(groupId),EventBean.createTestEventBean());
    }

    @Override
    public void webhookSendEvent(String groupId,EventBean eventBean) {
        try {
            restTemplate.postForObject(groupService.getWebhook(groupId), eventBean,String.class);
        }catch (Exception e){
            System.err.printf("Webhook error: can't send event to %s, with exception:%s,print by %s",groupService.getWebhook(groupId),e.getMessage(),this.getClass());
        }
    }

//    public static void main(String[] args) {
//        String s;
//        s = restTemplate.getForObject("http://140.136.149.165:8080",String.class);
//        System.out.println(s);
//    }
    @Override
    public void webhookSendEvent(String instantMessagingSoftware, String instantMessagingSoftwareUserId, EventBean eventBean) {
        if(eventBean.getEventType().equals("Transfer")) return;
        List<Group> groups= memberService.getGroups(instantMessagingSoftware,instantMessagingSoftwareUserId);
        for(Group group:groups){
            webhookSendEvent(group.getGroupId(),eventBean);
        }
    }
}
