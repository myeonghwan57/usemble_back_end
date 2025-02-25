package com.mycompany.webapp.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class Member {
	private String mid;
	private String mpassword;
	private String mname;
	private String mbirth;
	private String msex;
	private String mphone;
	private String mbankName;
	private String mpayAccount;
	private String mintroduce;
	private String mrole;
	private boolean menabled;
	private Date mdate;
	private boolean agree;

	private MultipartFile mattach;
	private byte[] mprofileData;
	private String mprofileType;
	private String mprofileName;
}
