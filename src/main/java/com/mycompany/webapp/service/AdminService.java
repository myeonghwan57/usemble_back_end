package com.mycompany.webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.CategoryDao;
import com.mycompany.webapp.dao.MemberDao;
import com.mycompany.webapp.dao.NoticeDao;
import com.mycompany.webapp.dao.ReviewDao;
import com.mycompany.webapp.dto.Category;
import com.mycompany.webapp.dto.Member;
import com.mycompany.webapp.dto.Notice;
import com.mycompany.webapp.dto.Review;

@Service
public class AdminService {
	@Autowired
	MemberDao memberDao;
	@Autowired
	CategoryDao categoryDao;
	@Autowired
	NoticeDao noticeDao;
	@Autowired
	ReviewDao reviewDao;

	// 멤버 탈퇴 여부 수정
	public void updateMemberEnable(Member member) {
		member.setMenabled(!member.isMenabled());
		int cnt = memberDao.updateMemberEnableByMid(member);
	}

	public List<Category> getCateName() {
		List<Category> categoryList = categoryDao.getCategory();
		return categoryList;
	}

	public List<Notice> getNotice() {
		List<Notice> noticeList = noticeDao.getNotice();
		return noticeList;
	}

	public List<Review> getReveiw() {
		List<Review> reviewList = reviewDao.getReview();
		return reviewList;
	}
}
