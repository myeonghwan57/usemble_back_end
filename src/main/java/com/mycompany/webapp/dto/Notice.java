package com.mycompany.webapp.dto;

import java.util.Date;

import lombok.Data;

@Data
public class Notice {
	private int nno;
	private String mid;
	private String ntitle;
	private String ncontent;
	private Date ndate;
}
