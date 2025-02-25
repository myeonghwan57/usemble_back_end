package com.mycompany.webapp.dao;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.Image;

@Mapper
public interface ImageDao {

	public void insertImage(Image image);

	public Image selectImageByName(String iname);

}
