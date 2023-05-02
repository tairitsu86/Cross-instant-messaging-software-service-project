package com.cimss.project.service.token;

import com.cimss.project.controller.exception.DataNotFoundException;

public enum InstantMessagingSoftwareList {
	LINE,TELEGRAM;
	public static InstantMessagingSoftwareList getInstantMessagingSoftwareToken(String instantMessagingSoftwareName){
		try {
			return InstantMessagingSoftwareList.valueOf(instantMessagingSoftwareName);
		}catch (IllegalArgumentException e){
			throw new DataNotFoundException("instantMessagingSoftware",instantMessagingSoftwareName);
		}
	}
}
