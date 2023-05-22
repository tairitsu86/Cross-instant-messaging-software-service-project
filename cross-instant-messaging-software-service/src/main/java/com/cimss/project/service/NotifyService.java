package com.cimss.project.service;

import com.cimss.project.apibody.EventBean;

public interface NotifyService {
    void notifyTest(String groupId);
    EventBean.ReplyEvent notify(String groupId,EventBean event);
}
