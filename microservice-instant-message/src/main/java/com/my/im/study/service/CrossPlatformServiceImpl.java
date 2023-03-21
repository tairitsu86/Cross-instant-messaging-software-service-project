package com.my.im.study.service;

import com.my.im.study.database.ManageService;
import com.my.im.study.database.MemberService;
import com.my.im.study.database.entity.User;
import com.my.im.study.linebot.LineMessageService;
import com.my.im.study.telegrambot.TelegramMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrossPlatformServiceImpl implements CrossPlatformService {
    @Autowired
    private MemberService memberService;
    @Autowired
    private LineMessageService lineMessageService;
    @Autowired
    private TelegramMessageService telegramMessageService;
    @Autowired
    private ManageService manageService;

    @Override
    public String broadcast(String userId,String groupId,String text) {
        if(!manageService.checkPermission(userId,groupId)) return "No permission!";
        List<User> users = memberService.getUsers(groupId);
        for(User user:users) {
            switch(InstantMessagingSoftwareList.valueOf(user.getInstantMessagingSoftware())) {
                case LINE:
                    lineMessageService.pushTextMessage(user.getInstantMessagingSoftwareUserId(),text);
                    break;
                case TELEGRAM:
                    telegramMessageService.sendTextMessage(Long.parseLong(user.getInstantMessagingSoftwareUserId()), text);
                    break;
            }
        }
        return "Broadcast done!";
    }
}
