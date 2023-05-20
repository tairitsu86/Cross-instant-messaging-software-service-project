package com.cimss.project.service.serviceImpl;

import com.cimss.project.database.entity.Group;
import com.cimss.project.database.entity.UserId;
import com.cimss.project.service.CIMSService;
import com.cimss.project.service.NotifyService;
import com.cimss.project.apibody.EventBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class NotifyServiceImpl implements NotifyService {
    private static final RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private CIMSService cimsService;

    @Override
    public void testWebhook(String groupId) {
        webhookSendEvent(groupId,EventBean.createTestEventBean());
    }

    @Override
    public void webhookSendEvent(String groupId,EventBean eventBean) {
//        try {
//            restTemplate.postForObject(cimsService.getGroupById(groupId).getGroupWebhook(), eventBean,String.class);
//        }catch (Exception e){
//            System.err.printf("Webhook error: can't send event to %s, with exception:%s,print by %s\n",cimsService.getGroupById(groupId).getGroupWebhook(),e.getMessage(),this.getClass());
//            return e.getMessage();
//        }
    }
    @Override
    public void webhookSendEvent(UserId userId, EventBean eventBean) {
        List<Group> groups= cimsService.getGroups(userId);
        if("TextMessage".equals(eventBean.getEventType())){
            EventBean.TextMessageEvent textMessageEvent = (EventBean.TextMessageEvent)eventBean;
//            for(Group group:groups){
//                if(group.getGroupKeyword()==null) continue;
//                if(!textMessageEvent.getMessage().matches("(?i)"+group.getGroupKeyword()+".*")) continue;
//                textMessageEvent.setIsManager(cimsService.isManager(textMessageEvent.getUser().toUserId(),group.getGroupId()));
//                webhookSendEvent(group.getGroupId(),textMessageEvent);
//            }
        }
    }

    @Override
    public void notify(String groupId, String message) {

    }
}
