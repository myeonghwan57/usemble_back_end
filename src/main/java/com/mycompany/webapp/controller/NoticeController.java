package com.mycompany.webapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.Notice;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.service.NoticeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/notice")
public class NoticeController {
	@Autowired
	NoticeService noticeService;

	@PostMapping("/write")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public Map<String, String> write(@RequestBody Notice notice) {
		Map<String, String> map = new HashMap<>();
		noticeService.write(notice);
		map.put("response", "success");

		return map;
	}

	@GetMapping("/read/{nno}") // http://localhost/read?bno=5
	public Notice read(@PathVariable int nno) {
		Notice notice = noticeService.readNotice(nno);

		return notice;
	}

	@PutMapping("/update")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public Notice update(@RequestBody Notice notice) {
		noticeService.update(notice);
		notice = noticeService.getNotice(notice.getNno());

		return notice;
	}

	@DeleteMapping("/delete/{nno}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public void delete(@PathVariable int nno) {
		noticeService.delete(nno);
	}

	// 전체 공지사항 목록 가져오기
	@GetMapping("/list")
	public Map<String, Object> list(@RequestParam(defaultValue = "1") int pageNo) {
		Map<String, Object> param = new HashMap<>();

		// 전체 공지사항 목록 가져오기
		List<Notice> allNotices = noticeService.getAllNotices();
		int totalRows = allNotices.size();

		// 페이징 처리
		Pager pager = new Pager(5, 5, totalRows, pageNo);
		param.put("pager", pager);

		// 페이징된 공지사항 목록 가져오기
		List<Notice> noticeList = noticeService.noticeByPage(param);

		// 결과 맵 구성
		Map<String, Object> map = new HashMap<>();
		map.put("response", "success");
		map.put("noticeList", noticeList);
		map.put("pager", pager);

		return map;
	}
}
