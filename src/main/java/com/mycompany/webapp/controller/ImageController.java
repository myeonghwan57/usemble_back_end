package com.mycompany.webapp.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.webapp.dto.Image;
import com.mycompany.webapp.service.ImageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/img")
public class ImageController {
	@Autowired
	ImageService imageService;

	@PostMapping("/upload")
	public Map<String, String> upload(MultipartFile img) {
		Map<String, String> map = new HashMap<>();

		Image image = new Image();

		// 파일 이름 설정
		image.setIname(new Date().getTime() + "-" + img.getOriginalFilename());
		// 파일 종류 설정
		image.setItype(img.getContentType());
		try {
			image.setIdata(img.getBytes());
			imageService.uploadImage(image);
		} catch (IOException e) {

		}

		// 이미지 경로 설정
		String imgUrl = "/img/download/" + image.getIname();

		map.put("response", "success");
		map.put("imgUrl", imgUrl);
		return map;
	}

	@GetMapping("/download/{iname}")
	public void dowload(@PathVariable String iname, HttpServletResponse response) {
		Image image = imageService.getImage(iname);

		try {
			String fileName = new String(image.getIname().getBytes("UTF-8"), "ISO-8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

			// 파일 타입을 헤더에 추가
			response.setContentType(image.getItype());
			// 응답 바디에 파일 데이터를 출력
			OutputStream os = response.getOutputStream();
			os.write(image.getIdata());
			os.flush();
			os.close();
		} catch (IOException e) {
			log.error(e.toString());
		}
	}
}
