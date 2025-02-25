package com.mycompany.webapp.dto;

import java.util.Date;

import lombok.Data;

@Data
public class Alarm {
	private int ano;
	private String mid;
	private String message;
	private Date adate;
	private boolean acheck;
}
