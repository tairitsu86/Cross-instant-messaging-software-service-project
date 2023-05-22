package com.cimss.project.handler;

import com.cimss.project.apibody.EventBean;
import com.cimss.project.database.entity.UserId;
import com.cimss.project.database.entity.token.GroupRole;
import com.cimss.project.database.entity.token.InstantMessagingSoftware;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

public interface EventHandler {
    void textEventHandler(UserId userId, String text);
    void commandEventHandler(UserId userId, String text);
    void replyEventHandler(UserId userId,String groupId,EventBean.ReplyEvent replyEvent);

    static CommandType GetCommandType(String type){
        CommandType commandType = null;
        try {
            commandType = CommandType.valueOf(type);
        }catch (IllegalArgumentException e){
            commandType = CommandType.UNKNOWN;
        }
        return commandType;
    }
    @Getter
    @AllArgsConstructor
    enum CommandType{
        UNKNOWN(null),


        GROUPS(null),
        KEY(null),
        SEARCH_GROUP(null),
        NEW_GROUP(null),
        PROFILE(null),
        EXIT(null),
        ALTER_USERNAME(null),



        NOTIFY(GroupRole.GROUP_MEMBER),
        TO_PATH(GroupRole.GROUP_MEMBER),
        LEAVE(GroupRole.GROUP_MEMBER),



        MEMBERS(GroupRole.GROUP_MANAGER),
        DETAIL(GroupRole.GROUP_MANAGER),
        BROADCAST(GroupRole.GROUP_MANAGER),
        REMOVE(GroupRole.GROUP_MANAGER),
        ALTER_GROUP_NAME(GroupRole.GROUP_MANAGER),
        ALTER_GROUP_DESCRIPTION(GroupRole.GROUP_MANAGER),
        ALTER_GROUP_IS_PUBLIC(GroupRole.GROUP_MANAGER),
        ALTER_GROUP_JOIN_BY_ID(GroupRole.GROUP_MANAGER),
        MANAGER_MENU(GroupRole.GROUP_MANAGER),


        ALTER_MEMBER_ROLE_OWNER(GroupRole.GROUP_OWNER),
        ALTER_MEMBER_ROLE_MANAGER(GroupRole.GROUP_OWNER),
        ALTER_MEMBER_ROLE_MEMBER(GroupRole.GROUP_OWNER),
        DELETE_GROUP(GroupRole.GROUP_OWNER);

        private GroupRole requireRole;

    }
    static Command CreateCommandObject(String command){
        String parameters[] = Arrays.copyOf(command.split(" ", 6),6);
        Command c = new Command(EventHandler.GetCommandType(parameters[1]),parameters[2], null, parameters[5]);
        try {
            c.userId = UserId.CreateUserId(InstantMessagingSoftware.getInstantMessagingSoftwareToken(parameters[3]),parameters[4]);
        }catch (Exception e){
        }
        return c;
    }
    static CommandBuilder CommandBuilder(CommandType commandType){
        return new CommandBuilder(commandType);
    }
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    class Command{
        private CommandType commandType;
        private String groupId;
        private UserId userId;
        private String parameter;

        @Override
        public String toString() {
            if(userId!=null)
                return String.format("/cimss %s %s %s %s %s",commandType.name(),groupId,userId.getInstantMessagingSoftware(),userId.getInstantMessagingSoftwareUserId(),parameter);
            else
                return String.format("/cimss %s %s %s %s %s",commandType.name(),groupId,null,null,parameter);
        }
    }

    class CommandBuilder{
        private Command command;
        public CommandBuilder(CommandType commandType){
            command = new Command(commandType,null,null,null);
        }
        public CommandBuilder setGroupId(String groupId){
            command.groupId = groupId;
            return this;
        }
        public CommandBuilder setUserId(UserId userId){
            command.userId = userId;
            return this;
        }
        public CommandBuilder setParameter(String parameter){
            command.parameter = parameter;
            return this;
        }
        public Command build(){
            return command;
        }
    }
}
