package com.mycompany.webapp.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.SjoinDao;
import com.mycompany.webapp.dao.SocialDao;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.Sjoin;
import com.mycompany.webapp.dto.Social;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SocialService {
	@Autowired
	SocialDao socialDao;

	@Autowired
	SjoinDao sjoinDao;

	public int getMemberSocialCntAll(String mid) {
		int socialCnt = socialDao.countAllByMid(mid);

		return socialCnt;
	}

	// 소셜 작성
	public void writeSocial(Social social) {
		socialDao.insert(social);
	}

	// 소셜 디테일 가져오기
	public Social getSocial(int sno) {
		Social social = socialDao.selectBySno(sno);
		return social;
	}

	// 소셜 리스트 가져오기
	public List<Social> getSocialList(Map<String, Object> param) {
		List<Social> socialList = socialDao.selectSocialListByParams(param);
		return socialList;
	}

	// 소셜 리스트 수 가져오기
	public int getSocialCntByParam(Map<String, Object> param) {
		int socialCnt = socialDao.countByCtno(param);
		return socialCnt;
	}

	// 소셜 썸네일 가져오기
	public Social getSthumb(int sno) {
		Social social = socialDao.selectSthumbBySno(sno);
		return social;
	}

	// 소셜 상태 업데이트
	public void updateStatus(Map<String, Object> param) {
		socialDao.updateSstatusBySno(param);
	}

	// 소셜 참가
	public Map<String, String> joinSocial(Sjoin sjoin) {
		int sno = sjoin.getSno();

		// 소셜의 데이터 가져오기
		Social social = socialDao.selectSpayInfoBySno(sno);

		// 현재 소셜에 참여한 인원 수 가져오기
		int currentSmember = sjoinDao.count(sno);

		Map<String, String> map = new HashMap<>();

		// 소셜 마감일
		Date deadline = social.getSdeadline();

		// 신청자 아이디와 작성자 아이디가 같으면 참여 불가
		if (social.getMid().equals(sjoin.getMid())) {
			map.put("response", "fail");
			map.put("reason", "same");
			return map;
		}

		// 소셜에 이미 참가했는지 확인
		if (sjoinDao.isSjoin(sjoin)) {
			map.put("response", "fail");
			map.put("reason", "alreadyJoin");
			return map;
		}

		// 소셜 마감일이 지나지 않고 모집 중이라면 참여 가능
		if (deadline.after(new Date()) && social.getSstatus().equals("recruitment")) {
			// 최대 인원이 현재 인원 보다 많다면 참여
			// 참여시 최대 인원이 되지 않을 때
			if (social.getSmemberCount() - 1 > currentSmember) {
				sjoinDao.insert(sjoin);
				map.put("response", "success");
			} else if (social.getSmemberCount() - 1 == currentSmember) {
				// 최대 인원이 됐다면 소셜 상태를 가득참으로 변경
				Map<String, Object> param = new HashMap<>();
				param.put("sno", sno);
				param.put("sstatus", "full");
				socialDao.updateSstatusBySno(param);

				sjoinDao.insert(sjoin);
				map.put("response", "success");
			} else {
				map.put("response", "fail");
				map.put("reason", "full");
			}
		} else {
			map.put("response", "fail");
			map.put("reason", "accessDenied");
		}

		return map;
	}

	public List<Social> getMainSocial() {
		List<Social> mainSocial = socialDao.getMainSocial();
		return mainSocial;
	}

	// 소셜 참여 수
	public int getSjoinCnt(int sno) {
		int sjoinCnt = sjoinDao.count(sno);
		return sjoinCnt;
	}

	// 소셜 결제 정보 가져오기
	public Social getSpayInfo(int sno) {
		Social sjoinInfo = socialDao.selectSpayInfoBySno(sno);
		return sjoinInfo;
	}

	// 소셜 참여 여부 가져오기
	public boolean getSjoinState(Sjoin sjoin) {
		boolean sjoinState = sjoinDao.isSjoin(sjoin);
		return sjoinState;
	}

	// 소셜 참가 취소
	public void cancelSjoin(Sjoin sjoin) {
		int sno = sjoin.getSno();

		// 소셜 결제 정보 가져오기
		Social social = socialDao.selectSpayInfoBySno(sno);

		// 소셜 현재 참가 인원 수 가져오기
		int currentSmember = sjoinDao.count(sno);

		// 소셜 마감일
		Date deadline = social.getSdeadline();

		// 현재 참가 인원이 가득, 마감일이 지나지 않았을 때 소셜의 상태를 변경하며 참여 취소
		if (social.getSmemberCount() == currentSmember && deadline.after(new Date())) {
			Map<String, Object> param = new HashMap<>();
			param.put("sno", social.getSno());
			param.put("sstatus", "recruitment");
			socialDao.updateSstatusBySno(param);
		}
		// 소셜 참가 취소
		int sjoinCnt = sjoinDao.deleteSjoin(sjoin);
	}

	// 소셜 참가 기록 가져오기
	public List<Social> getJoinHistory(Map<String, Object> param) {
		List<Social> joinHistory = socialDao.selectJoinHistoryByPager(param);
		return joinHistory;
	}

	// 소셜 참가 기록 수 가져오기
	public int getJoinHistoryCnt(String mid) {
		int joinHistoryCnt = socialDao.selectJoinHistoryCnt(mid);
		return joinHistoryCnt;
	}

	// 소셜 모집 기록 가져오기
	public List<Social> getRecruitHistory(Map<String, Object> param) {
		List<Social> recruitHistory = socialDao.selectRecruitHistoryByPager(param);
		return recruitHistory;
	}

	// 소셜 모집 기록 수 가져오기
	public int getRecruitHistoryCnt(String mid) {
		int recruitHistoryCnt = socialDao.selectRecruitHistoryCnt(mid);
		return recruitHistoryCnt;
	}

	public List<Social> getApplyAssemble(String mid) {
		List<Social> socialList = socialDao.getApplyAssemble(mid);
		return socialList;
	}

	// 소셜 마감 여부 가져오기
	public boolean isDeadline(int sno) {
		Date deadline = socialDao.selectIsDeadlineBySno(sno);
		boolean isDeadline = deadline.before(new Date());
		return isDeadline;
	}

	// 검색 리스트 가져오기
	public List<Social> getSearchList(Map<String, Object> param) {
		List<Social> searchList = socialDao.selectSearchByStitle(param);
		return searchList;
	}

	// 검색한 소셜 수 가져오기
	public int getSocialCntByKeyword(String keyword) {
		int socialCnt = socialDao.selectByKeyword(keyword);
		return socialCnt;
	}

	public List<Social> getMainCateSocial(String mid) {
		List<Social> socialList = socialDao.getMainCateSocial(mid);
		return socialList;
	}

	// 소셜 수 가져오기
	public int getCount() {
		return socialDao.countAll();
	}

	// 소셜 리스트 가져오기
	public List<Social> getList(Pager pager) {
		return socialDao.selectByPage(pager);
	}

	// 관리자 페이지 사용자 소셜 모집 리스트 가져오기
	public List<Map<String, Object>> getRecruitSocialPageInAdmin(Map<String, Object> param) {
		List<Map<String, Object>> socialList = socialDao.selectRecruitInAdminByPager(param);
		return socialList;
	}

	// 관리자 페이지 사용자 소셜 참여 리스트 가져오기
	public List<Map<String, Object>> getJoinSocialPageInAdmin(Map<String, Object> param) {
		List<Map<String, Object>> socialList = socialDao.selectJoinInAdminByPager(param);
		return socialList;
	}

	public List<Social> getRecruitAssemble(String mid) {
		List<Social> socialList = socialDao.getRecruitAssemble(mid);
		return socialList;
	}

	public List<Social> getRecruitedAssemble(String mid) {
		List<Social> socialList = socialDao.getRecruitedAssemble(mid);
		return socialList;
	}

	public List<Social> getInprogress(Map<String, Object> param) {
		List<Social> socialList = socialDao.getInprogress(param);
		return socialList;
	}

	public List<Social> getProgressed(Map<String, Object> param) {
		List<Social> socialList = socialDao.getProgressed(param);
		return socialList;
	}

	public int getInprogressCnt(Map<String, Object> param) {
		int inprogressCnt = socialDao.countInprogress(param);
		return inprogressCnt;
	}

	public int getProgessedCnt(Map<String, Object> param) {
		int progressedCnt = socialDao.countProgressed(param);
		return progressedCnt;
	}

	public int getCountByKeyword(Map<String, Object> param) {
		int socialCnt = socialDao.countSocialByKeyword(param);
		return socialCnt;
	}

	public List<Social> getListByKeyword(Map<String, Object> param) {
		List<Social> socialList = socialDao.selectSocialByKeywordPage(param);
		return socialList;
	}

	public List<Social> getCountAll() {
		List<Social> socialList = socialDao.selectAllSocial();
		return socialList;
	}

}
