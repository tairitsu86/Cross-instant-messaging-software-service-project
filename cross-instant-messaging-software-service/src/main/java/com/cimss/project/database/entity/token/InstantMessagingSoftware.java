package com.cimss.project.database.entity.token;

import com.cimss.project.controller.exception.DataNotFoundException;

import java.io.Serializable;

public enum InstantMessagingSoftware implements Serializable {
	LINE,TELEGRAM;
	public static InstantMessagingSoftware getInstantMessagingSoftwareToken(String instantMessagingSoftwareName){
		try {
			return InstantMessagingSoftware.valueOf(instantMessagingSoftwareName);
		}catch (IllegalArgumentException e){
			throw new DataNotFoundException("instantMessagingSoftware",instantMessagingSoftwareName);
		}
	}
}
