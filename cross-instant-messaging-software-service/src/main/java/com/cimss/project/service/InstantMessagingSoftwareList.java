package com.cimss.project.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public enum InstantMessagingSoftwareList {
	LINE("LINE"),TELEGRAM("TELEGRAM");
	private String name;
}
