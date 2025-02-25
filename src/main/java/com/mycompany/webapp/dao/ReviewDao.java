package com.mycompany.webapp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.Review;

@Mapper
public interface ReviewDao {

	public int count();

	public int insert(Review review);

	public Review selectReviewByParam(Review review);

	public boolean selectIsReviewByParam(Review review);

	public int updateReview(Review review);

	public int deleteReview(Review review);

	public List<Map<String, Object>> selectRecieveReviewByMid(Map<String, Object> param);

	public int countRecieveReview(String mid);

	public int countLeaveReview(String mid);

	public List<Map<String, Object>> selectLeaveReviewByMid(Map<String, Object> param);

	public int countAll();

	public List<Review> selectByPage(Pager pager);

	public List<Review> getReview();

}
