package com.mycompany.webapp.dto;

import java.util.Date;

import lombok.Data;

@Data
public class Review {
	private int sno;
	private String mid;
	private String rcontent;
	private Date rdate;
}
