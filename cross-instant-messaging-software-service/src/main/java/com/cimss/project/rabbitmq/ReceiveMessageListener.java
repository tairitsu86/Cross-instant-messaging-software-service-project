package com.cimss.project.rabbitmq;

import com.cimss.project.apibody.EventBean;
import com.cimss.project.handler.EventHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReceiveMessageListener {

    @Autowired
    private EventHandler eventHandler;

    @RabbitListener(queues={"cimss/reply"})
    public void receive(EventBean.ReplyEvent replyEvent) {
        System.out.println(replyEvent.toString());
        eventHandler.replyEventHandler(replyEvent);
    }
}
