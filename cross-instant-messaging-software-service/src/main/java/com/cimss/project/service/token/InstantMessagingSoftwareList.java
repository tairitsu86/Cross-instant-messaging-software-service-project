package com.cimss.project.service.token;

public enum InstantMessagingSoftwareList {
	LINE,TELEGRAM;
	public static InstantMessagingSoftwareList getInstantMessagingSoftwareToken(String instantMessagingSoftwareName){
		try {
			return InstantMessagingSoftwareList.valueOf(instantMessagingSoftwareName);
		}catch (IllegalArgumentException e){
			return null;
		}
	}
}
