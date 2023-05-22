package com.cimss.project.handler;

import com.cimss.project.database.entity.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public interface WaitingEventHandler {
    boolean checkWaiting(UserId userId, String data);

    void addWaitingUser(UserId userId,WaitingType waitingType,String tempData);

    boolean exitWaiting(UserId userId);

    static MetaData CreateMetadata(WaitingType waitingType,String tempData){
        return new MetaData(waitingType,tempData);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    class MetaData{
        private WaitingType waitingType;
        private String tempData;

    }
    enum WaitingType{
        INIT_USERNAME,
        ALTER_USERNAME,
        SEARCH_KEYWORD,
        MENU_GROUP_ID,
        JOIN_GROUP_ID,
        CREATE_GROUP_NAME,
        ALTER_GROUP_NAME,
        ALTER_GROUP_DESCRIPTION,
        ALTER_GROUP_IS_PUBLIC,
        ALTER_GROUP_JOIN_BY_ID,
        BROADCAST_MESSAGE,
        REMOVE_MEMBER_IM_USER_ID,
        TO_MEMBER_IM_USER_ID,
        TO_MANAGER_IM_USER_ID,
        TO_OWNER_IM_USER_ID;
    }

}
