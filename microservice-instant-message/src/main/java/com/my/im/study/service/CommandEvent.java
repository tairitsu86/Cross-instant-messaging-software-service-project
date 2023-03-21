package com.my.im.study.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.my.im.study.database.GroupService;
import com.my.im.study.database.MemberService;
import com.my.im.study.database.UserService;
import com.my.im.study.database.entity.Group;
import com.my.im.study.database.entity.User;
import com.my.im.study.linebot.LineMessageService;
import com.pengrad.telegrambot.model.Message;

@Service
public class CommandEvent {
	@Autowired
    private UserService userService;
	@Autowired
    private GroupService groupService;
	@Autowired
    private MemberService memberService;
    @Autowired
    private CrossPlatformService crossPlatformService;
    @Autowired
    private LineMessageService lineMessageService;
    
    private final String CHATBOTCOMMAND, ERRORMESSAGE; 
    
    public CommandEvent() {
    	StringBuilder fileTemp = new StringBuilder("");
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/ChatBotCommand.txt"), "UTF-8"));
			String temp;
			while((temp=br.readLine())!=null) {
				fileTemp.append(temp+"\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		CHATBOTCOMMAND = fileTemp.toString();
		ERRORMESSAGE = "Error command!\nCheck you write in right format!\n"+CHATBOTCOMMAND; 
    }
    
	public String handleCommandEvent(Object commandEvent,InstantMessagingSoftwareList instantMessagingSoftware) {
		//userId is the object User's pk
		//instantMessageId is the client user id from any instant message software
		String userId=null,
				command=null,
				instantMessageUserId=null,
				name=null,
				instantMessagingSoftwareName= instantMessagingSoftware.getName(),
				parameter[],
				executeResult = ERRORMESSAGE;
		int parameterNumber;
		switch(instantMessagingSoftware) {
		case LINE:
			MessageEvent<TextMessageContent> event = (MessageEvent<TextMessageContent>) commandEvent;
			command = event.getMessage().getText();
			instantMessageUserId = event.getSource().getUserId();
			userId = instantMessagingSoftwareName+instantMessageUserId;
			name = lineMessageService.getUserProfile(instantMessageUserId).getDisplayName();
			break;
		case TELEGRAM:
			Message message = (Message) commandEvent;
			command = message.text();
			instantMessageUserId = message.chat().id().toString();
			userId = instantMessagingSoftwareName+instantMessageUserId;
			name = message.chat().lastName()+message.chat().firstName();
			break;
		default:
			return "";
		}
		
		parameter = command.split(" ");
		parameterNumber = parameter.length-1;	
		
//    	if(command.startsWith("/newgroup")) {
//        	if(parameterNumber!=1) return ERRORMESSAGE;
//        	Group newGroup = groupService.createGroup(new Group(null,parameter[1]));
//        	executeResult = String.format("New group success!\nThere is your group id:\n%s",newGroup.getGroupId());
//        }else if(command.startsWith("/join")) {
//        	if(parameterNumber!=1) return ERRORMESSAGE;
//        	userService.createUser(new User(userId, name, instantMessagingSoftwareName, instantMessageUserId));
//        	Group group = groupService.getGroupById(parameter[1]);
//        	if(group==null) return "Group not exist!";
//        	memberService.join(userId, parameter[1]);
//        	executeResult = String.format("Join group %s success!",group.getGroupName());
//        }else if(command.startsWith("/mygroups")) {
//        	List<Group> groups = memberService.getGroups(userId);
//        	if(groups.size()==0) return "Not in any group!";
//        	executeResult = "Groups:";
//        	for(Group group:groups)
//        		executeResult += String.format("\ngroup: %s\ngroup id:%s\n",group.getGroupName(),group.getGroupId());
//        }else if(command.startsWith("/groupmembers")) {
//        	if(parameterNumber!=1) return ERRORMESSAGE;
//        	Group group = groupService.getGroupById(parameter[1]);
//        	if(group==null) return "Group not exist!";
//        	List<User> users = memberService.getUsers(parameter[1]);
//        	if(users.size()==0) return "No member in this group!";
//        	executeResult = "Members:";
//        	for(User user:users)
//        		executeResult += String.format("\nuser: %s\nuser id:%s\nfrom:%s\n",user.getUserName(),user.getInstantMessagingSoftwareUserId(),user.getInstantMessagingSoftware());
//        }else if(command.startsWith("/broadcast")) {
//        	if(parameterNumber!=2) return ERRORMESSAGE;
//			crossPlatformService.broadcast("OWO",parameter[1], parameter[2]);
//        	executeResult = String.format("Broadcast done!");
//        }else if(command.startsWith("/leave")) {
//        	if(parameterNumber!=1) return ERRORMESSAGE;
//        	executeResult = "Not yet implemented!";
//        }else if(command.startsWith("/?")) {
//        	executeResult = CHATBOTCOMMAND;
//        }
    	return executeResult;
    }
}
