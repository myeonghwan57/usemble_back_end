package com.mycompany.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.ImageDao;
import com.mycompany.webapp.dto.Image;

@Service
public class ImageService {
	@Autowired
	ImageDao imageDao;

	// 이미지 업로드
	public void uploadImage(Image image) {
		imageDao.insertImage(image);
	}

	// 이미지 가져오기
	public Image getImage(String iname) {
		Image image = imageDao.selectImageByName(iname);
		return image;
	}

}
