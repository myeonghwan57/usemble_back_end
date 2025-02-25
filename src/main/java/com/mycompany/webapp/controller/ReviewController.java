package com.mycompany.webapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.Review;
import com.mycompany.webapp.service.ReviewService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/review")
public class ReviewController {
	@Autowired
	ReviewService reviewService;

	// 리뷰 작성
	@PostMapping("/write")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Map<String, String> writeReview(@RequestBody Review review) {
		Map<String, String> map = new HashMap<>();

		// 리뷰 작성하기
		reviewService.write(review);

		map.put("response", "success");

		return map;
	}

	// 리뷰 작성 여부
	@GetMapping("/state")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Map<String, Object> isReview(Review review) {
		// 리뷰 작성 여부 가져오기
		boolean isReview = reviewService.isReview(review);

		Map<String, Object> map = new HashMap<>();

		map.put("response", "success");
		map.put("isReview", isReview);

		return map;
	}

	// 리뷰 읽기
	@GetMapping("/read")
	public Map<String, Object> readReview(Review review) {
		// 리뷰 가져오기
		Review data = reviewService.readReview(review);

		Map<String, Object> map = new HashMap<>();

		map.put("response", "success");
		map.put("review", data);

		return map;
	}

	// 리뷰 수정
	@PatchMapping("/update")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Map<String, Object> updateReview(@RequestBody Review review) {
		// 리뷰 수정하기
		reviewService.updateReview(review);

		Map<String, Object> map = new HashMap<>();

		map.put("response", "success");

		return map;
	}

	// 리뷰 삭제
	@DeleteMapping("/delete")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Map<String, Object> deleteReview(Review review) {
		// 리뷰 삭제하기
		reviewService.deleteReview(review);

		Map<String, Object> map = new HashMap<>();

		map.put("response", "success");

		return map;
	}

	// 받은 리뷰 페이지
	@GetMapping("/recieve")
	public Map<String, Object> recieveReview(@RequestParam(defaultValue = "1") int rPageNo, String mid) {
		// 받은 리뷰 수 가져오기
		int totalRows = reviewService.getRecieveReviewCnt(mid);

		Pager pager = new Pager(4, 5, totalRows, rPageNo);

		Map<String, Object> param = new HashMap<>();

		param.put("mid", mid);
		param.put("pager", pager);

		// 리뷰 리스트 페이지 가져오기
		List<Map<String, Object>> reviewList = reviewService.getRecieveReviewList(param);

		Map<String, Object> map = new HashMap<>();

		map.put("response", "success");
		map.put("pager", pager);
		map.put("reviewList", reviewList);

		return map;
	}

	// 작성한 리뷰 페이지
	@GetMapping("/leave")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Map<String, Object> leaveReview(@RequestParam(defaultValue = "1") int lPageNo, String mid) {
		// 작성한 리뷰 수 가져오기
		int totalRows = reviewService.getLeaveReviewCnt(mid);

		Pager pager = new Pager(4, 5, totalRows, lPageNo);

		Map<String, Object> param = new HashMap<>();

		param.put("mid", mid);
		param.put("pager", pager);

		// 작성한 리뷰 페이지 가져오기
		List<Map<String, Object>> reviewList = reviewService.getLeaveReviewList(param);

		Map<String, Object> map = new HashMap<>();

		map.put("response", "success");
		map.put("pager", pager);
		map.put("reviewList", reviewList);

		return map;
	}
}
