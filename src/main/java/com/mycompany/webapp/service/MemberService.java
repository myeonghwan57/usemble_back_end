package com.mycompany.webapp.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.AlarmDao;
import com.mycompany.webapp.dao.CategoryDao;
import com.mycompany.webapp.dao.McategoryDao;
import com.mycompany.webapp.dao.MemberDao;
import com.mycompany.webapp.dao.MlikeDao;
import com.mycompany.webapp.dto.Alarm;
import com.mycompany.webapp.dto.Category;
import com.mycompany.webapp.dto.Mcategory;
import com.mycompany.webapp.dto.Member;
import com.mycompany.webapp.dto.Mlike;
import com.mycompany.webapp.dto.Pager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberService {
	@Autowired
	MlikeDao mlikeDao;

	@Autowired
	MemberDao memberDao;

	@Autowired
	CategoryDao categoryDao;

	@Autowired
	McategoryDao mcategoryDao;

	@Autowired
	AlarmDao alarmDao;

	// 좋아요 리스트 가져오기
	public List<String> getLikeList(Map<String, Object> map) {
		List<String> likeList = mlikeDao.selectLikeListByMid(map);
		return likeList;
	}

	// 사용자 공용 프로필 정보 가져오기
	public Member getProfile(String mid) {
		Member member = memberDao.selectOfficialProfileByMid(mid);
		return member;
	}

	// 사용자가 좋아요 한 수 가져오기
	public int getMyLikeCnt(String mid) {
		int mlikeCnt = mlikeDao.myLikeCount(mid);
		return mlikeCnt;
	}

	// 사용자가 좋아요 받은 수 가져오기
	public int getLikeCnt(String mid) {
		int mlikeCnt = mlikeDao.count(mid);
		return mlikeCnt;
	}

	// 사용자가 좋아요 했는지 가져오기
	public boolean getLikeState(Mlike mlike) {
		boolean likeState = mlikeDao.selectLikeByMidFmid(mlike);
		return likeState;
	}

	// 좋아요 해제하기
	public int deleteLike(Mlike mlike) {
		int like = mlikeDao.deleteLikeByMidFmid(mlike);
		return like;
	}

	// 좋아요
	public int like(Mlike mlike) {
		int like = mlikeDao.insertLikeByMidFmid(mlike);
		return like;
	}

	// 회원 가입
	public void join(Member member) {
		memberDao.insert(member);
	}

	// 회원 탈퇴
	public int withdrawMember(String mid) {
		int withdraw = memberDao.withdrawMemberByMid(mid);
		return withdraw;
	}

	// 동일 계정 유무 확인
	public int checkId(String mid) {
		int result = memberDao.checkId(mid);
		return result;
	}

	// 프로필 이미지 가져오기
	public Member getMemberAttach(String mid) {
		Member member = memberDao.attachSelectByMid(mid);
		return member;
	}

	// 프로필 수정
	public void updateProfile(Member member) {
		int mupdate = memberDao.updateProfileByMid(member);
	}

	// 비밀번호 수정
	public void updateMpassword(Member member) {
		int mupdate = memberDao.updateMpasswordByMid(member);
	}

	// 임시 비밀번호 발급
	public String getTempPassword(Member member) {
		// 사용자 정보 얻기 - 아이디
		// 임시 비밀번호 생성
		// 임시 비밀번호 글자 수
		int passwordLength = 8;
		// 임시 비밀번호에 들어갈 수 있는 글자 테이블
		char[] passwordTable = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
				'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
				'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '!', '@', '#', '$', '%', '^', '&',
				'*', '(', ')', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };

		// 현재 시각을 시드로 랜덤 객체 생성
		Random random = new Random(System.currentTimeMillis());
		int tableLength = passwordTable.length;

		StringBuffer buf = new StringBuffer();

		// StringBuffer에 임시 비밀번호 글자 수만큼 passwordTable 속 글자를 랜덤하게 추가
		for (int i = 0; i < passwordLength; i++) {
			buf.append(passwordTable[random.nextInt(tableLength)]);
		}

		// 임시 비밀번호 저장
		String tempPassword = buf.toString();

		// 임시 비밀번호 암호화를 위해 PasswordEncoder 객체 가져오기
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

		// 멤버 객체에 신규 비밀번호 암호화 후 설정
		member.setMpassword(passwordEncoder.encode(tempPassword));

		// 멤버 비밀번호 업데이트
		memberDao.updateMpasswordByMid(member);

		return tempPassword;
	}

	// 사용자 개인정보 수정
	public void updatePrivacy(Member member) {
		int mupdate = memberDao.updatePrivacy(member);
	}

	// 사용자 개인정보 가져오기
	public Member getPrivacy(String mid) {
		Member member = memberDao.selectPrivacyByMid(mid);
		return member;
	}

	public List<Category> getCategory() {
		List<Category> categoryList = categoryDao.getCategory();
		return categoryList;
	}

	// 사용자 선택 동의 수정
	public void updateAgree(Member member) {
		int mupdate = memberDao.updateAgree(member);
	}

	public List<Mcategory> getMcategory(String mid) {
		List<Mcategory> mcategory = mcategoryDao.getMcategory(mid);
		return mcategory;
	}

	public void putMcategory(List<Mcategory> mcategory) {
		for (int i = 0; i < mcategory.size(); i++) {
			mcategoryDao.putMcategory(mcategory.get(i));
		}
	}

	public void updateMcategory(List<Mcategory> mcategory) {
		mcategoryDao.deleteMcategory(mcategory.get(0).getMid());
		Iterator<Mcategory> iter = mcategory.iterator();
		while (iter.hasNext()) {
			int mcategoryList = mcategoryDao.updateMcategory(iter.next());
		}
	}

	public void putAgree(String mid) {
		int pta = memberDao.putAgree(mid);
	}

	public void deleteMcategory(String mid) {
		mcategoryDao.deleteMcategory(mid);
	}

	// 소셜에 참여한 사용자 리스트 가져오기
	public List<Member> getJoinMember(int sno) {
		List<Member> memberList = memberDao.getJoinMemberBySno(sno);
		return memberList;
	}

	// 사용자 리스트 페이지 가져오기
	public List<Member> getList(Pager pager) {
		return memberDao.selectByPage(pager);
	}

	public int getCount() {
		return memberDao.countAll();
	}

	// 사용자에게 알림 보내기
	public void sendAlarm(String mid, String message) {
		Alarm alarm = new Alarm();
		alarm.setMid(mid);
		alarm.setMessage(message);
		alarm.setAcheck(false);

		int cnt = alarmDao.insert(alarm);
	}

	// 사용자가 미확인한 알림 수 가져오기
	public int getAlarmCntByMid(String mid) {
		int alarmCnt = alarmDao.countAlarmByMid(mid);
		return alarmCnt;
	}

	// 사용자가 미확인한 알림 가져오기
	public List<Alarm> getAlarmList(Map<String, Object> param) {
		List<Alarm> alarmList = alarmDao.selectAlarmByPager(param);
		return alarmList;
	}

	// 알림 확인
	public void checkAlarm(int ano) {
		alarmDao.updateAlarmByAno(ano);
	}

	// 미확인 알림 여부 가져오기
	public boolean isAlarm(String mid) {
		boolean isAlarm = alarmDao.getAlarmStateByMid(mid);
		return isAlarm;
	}

	// 어드민 페이지의 사용자 정보 가져오기
	public Member getMemberInfo(String mid) {
		Member member = memberDao.selectMemberAdminByMid(mid);
		return member;
	}

	// 키워드에 맞는 사용자 수 세기
	public int getCountByKeyword(Map<String, Object> param) {
		int memberCnt = memberDao.countMemberByKeyword(param);
		return memberCnt;
	}

	// 키워드에 맞는 사용자 리스트 가져오기
	public List<Member> getListByKeyword(Map<String, Object> param) {
		List<Member> memberList = memberDao.selectMemberByKeywordPage(param);
		return memberList;
	}

	public List<Member> getCountAll() {
		List<Member> memberList = memberDao.selectAllMemeber();
		return memberList;
	}

}
