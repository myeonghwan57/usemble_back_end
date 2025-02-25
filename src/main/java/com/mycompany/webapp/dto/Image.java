package com.mycompany.webapp.dto;

import lombok.Data;

@Data
public class Image {
	private String iname;
	private String itype;
	private byte[] idata;
}
