package com.mycompany.webapp.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class Social {
	private int sno;
	private String mid;
	private int ctno;
	private String stitle;
	private String scontent;
	private byte[] sthumbData;
	private String sthumbType;
	private String sthumbName;
	private Date swriteDate;
	private Date sdeadline;
	private Date sstartDate;
	private String sstartTime;
	private int sfee;
	private int smemberCount;
	private String sstatus;
	private String saddress;

	private MultipartFile sthumbnail;

	private boolean review;

}
