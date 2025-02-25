package com.mycompany.webapp.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.ReviewDao;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.Review;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReviewService {
	@Autowired
	ReviewDao reviewDao;

	// 리뷰 작성
	public void write(Review review) {
		int reviewCnt = reviewDao.insert(review);
	}

	// 리뷰 작성 여부 가져오기
	public boolean isReview(Review review) {
		boolean isReview = reviewDao.selectIsReviewByParam(review);
		return isReview;
	}

	// 리뷰 읽기
	public Review readReview(Review review) {
		Review data = reviewDao.selectReviewByParam(review);
		return data;
	}

	// 리뷰 수정
	public void updateReview(Review review) {
		int reviewCnt = reviewDao.updateReview(review);
	}

	// 리뷰 삭제
	public void deleteReview(Review review) {
		int reviewCnt = reviewDao.deleteReview(review);
	}

	// 받은 리뷰 가져오기
	public List<Map<String, Object>> getRecieveReviewList(Map<String, Object> param) {
		List<Map<String, Object>> reviewList = reviewDao.selectRecieveReviewByMid(param);
		return reviewList;
	}

	// 받은 리뷰 수 가져오기
	public int getRecieveReviewCnt(String mid) {
		int recieveReivewCnt = reviewDao.countRecieveReview(mid);
		return recieveReivewCnt;
	}

	// 남긴 리뷰 가져오기
	public List<Map<String, Object>> getLeaveReviewList(Map<String, Object> param) {
		List<Map<String, Object>> reviewList = reviewDao.selectLeaveReviewByMid(param);
		return reviewList;
	}

	// 남긴 리뷰 수 가져오기
	public int getLeaveReviewCnt(String mid) {
		int leaveReviewCnt = reviewDao.countLeaveReview(mid);
		return leaveReviewCnt;
	}

	// 모든 리뷰 수 가져오기
	public int getCount() {
		return reviewDao.countAll();
	}

	// 리뷰 리스트 가져오기
	public List<Review> getList(Pager pager) {
		return reviewDao.selectByPage(pager);
	}

}
