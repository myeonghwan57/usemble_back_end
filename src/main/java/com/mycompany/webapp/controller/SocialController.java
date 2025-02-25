package com.mycompany.webapp.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.webapp.dto.Member;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.Sjoin;
import com.mycompany.webapp.dto.Social;
import com.mycompany.webapp.service.MemberService;
import com.mycompany.webapp.service.SocialService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/social")
public class SocialController {
	@Autowired
	SocialService socialService;

	@Autowired
	MemberService memberService;

	// 소셜 작성
	@PostMapping("/write")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Map<String, String> write(Social social) {
		Map<String, String> map = new HashMap<>();

		MultipartFile mf = social.getSthumbnail();
		// 파일 이름 설정
		social.setSthumbName(mf.getOriginalFilename());
		// 파일 종류 설정
		social.setSthumbType(mf.getContentType());
		try {
			social.setSthumbData(mf.getBytes());
			mf.getInputStream().close();
		} catch (IOException e) {

		}

		// 시작일 기준 하루 전으로 마감일 설정
		Date sstartDate = social.getSstartDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sstartDate);
		calendar.add(calendar.DATE, -1);

		// 작성시 소셜 모집 중으로 상태 설정
		social.setSstatus("recruitment");
		social.setSdeadline(calendar.getTime());

		// 소셜 작성
		socialService.writeSocial(social);

		map.put("response", "success");
		return map;
	}

	// 소셜 읽기
	@GetMapping("/read/{sno}")
	public Map<String, Object> read(@PathVariable int sno) {
		Map<String, Object> map = new HashMap<>();

		// 소셜 가져오기
		Social social = socialService.getSocial(sno);

		map.put("response", "success");
		map.put("social", social);

		return map;
	}

	// 소셜 리스트 페이지
	@GetMapping("/list")
	public Map<String, Object> list(@RequestParam(defaultValue = "1") int pageNo,
			@RequestParam(defaultValue = "0") int ctno, String sort) {
		Map<String, Object> param = new HashMap<>();
		if (ctno != 0) {
			param.put("ctno", ctno);
		}

		// 카테고리별 소셜 개수 가져오기
		int totalRows = socialService.getSocialCntByParam(param);

		Pager pager = new Pager(9, 5, totalRows, pageNo);

		// 정렬 기준
		if (sort != null) {
			param.put("sort", sort);
		}
		param.put("pager", pager);

		// 파라미터 기준으로 소셜 리스트 페이지 가져오기
		List<Social> socialList = socialService.getSocialList(param);

		Map<String, Object> map = new HashMap<>();

		map.put("response", "success");
		map.put("socialList", socialList);
		map.put("pager", pager);

		return map;
	}

	// 소셜 취소
	@PatchMapping("/delete/{sno}")
	public Map<String, String> deleteSocial(@PathVariable int sno) {
		Map<String, Object> param = new HashMap<>();
		param.put("sno", sno);
		param.put("sstatus", "cancel");

		// 소셜 상태 취소로 업데이트
		socialService.updateStatus(param);

		// 사용자한테 알림 메세지를 보내기 위해 데이터 가져오기
		List<Member> memberList = memberService.getJoinMember(sno);
		Social social = socialService.getSpayInfo(sno);

		// 취소한 소셜에 참가한 사용자 전체에 알림 메세지 보내기
		Iterator<Member> iter = memberList.iterator();
		while (iter.hasNext()) {
			memberService.sendAlarm(iter.next().getMid(), "\"" + social.getStitle() + "\"" + " 호스트가 어셈블을 취소했습니다. :(\n");
		}

		Map<String, String> map = new HashMap<>();
		map.put("response", "success");

		return map;
	}

	// 소셜 썸네일
	@GetMapping("/sthumb/{sno}")
	public void sthumb(@PathVariable int sno, HttpServletResponse response) {
		Social social = socialService.getSthumb(sno);

		try {
			String fileName = new String(social.getSthumbName().getBytes("UTF-8"), "ISO-8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

			// 파일 타입을 헤더에 추가
			response.setContentType(social.getSthumbType());
			// 응답 바디에 파일 데이터를 출력
			OutputStream os = response.getOutputStream();
			os.write(social.getSthumbData());
			os.flush();
			os.close();
		} catch (IOException e) {
			log.error(e.toString());
		}
	}

	// 소셜 참가
	@PostMapping("/sjoin")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Map<String, String> sjoin(@RequestBody Sjoin sjoin) {
		Map<String, String> isJoin = socialService.joinSocial(sjoin);

		// 참가한 소셜의 주최자에게 알림 메시지 전달
		Social social = socialService.getSpayInfo(sjoin.getSno());
		if (isJoin.get("response") == "success") {
			memberService.sendAlarm(social.getMid(),
					sjoin.getMid() + "님이 " + "\"" + social.getStitle() + "\"" + " 어셈블에 참가하셨습니다. :)\n");
		}

		return isJoin;
	}

	// 소셜 참가 인원 수
	@GetMapping("/sjoin/count/{sno}")
	public Map<String, Object> sjoinCnt(@PathVariable int sno) {
		Map<String, Object> map = new HashMap<>();

		// 소셜 참가 인원 수 가져오기
		int sjoinCnt = socialService.getSjoinCnt(sno);

		map.put("response", "success");
		map.put("sjoinCnt", sjoinCnt);

		return map;
	}

	// 소셜 결제 페이지
	@GetMapping("/pay/{sno}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Map<String, Object> sjoinInfo(@PathVariable int sno) {
		Map<String, Object> map = new HashMap<>();
		// 만약 sno가 0으로 전달 되면 실패 응답
		if (sno == 0) {
			map.put("response", "fail");
			return map;
		}
		// 소셜 결제 페이지의 데이터 가져오기
		Social spayInfo = socialService.getSpayInfo(sno);

		map.put("response", "success");
		map.put("spayInfo", spayInfo);

		return map;
	}

	// 소셜 참가 여부
	@GetMapping("/sjoin/state")
	public Map<String, Object> sjoinState(Sjoin sjoin) {
		// 현재 사용자의 참가 여부 가져오기
		boolean sjoinState = socialService.getSjoinState(sjoin);

		Map<String, Object> map = new HashMap<>();

		map.put("response", "success");
		map.put("sjoinState", sjoinState);

		return map;
	}

	@GetMapping("/mainSocial")
	public List<Social> mainSocial() {
		List<Social> socialList = socialService.getMainSocial();
		return socialList;
	}

	@DeleteMapping("/sjoin/cancel")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Map<String, String> cancelSjoin(Sjoin sjoin) {
		// 소셜 참가 취소
		socialService.cancelSjoin(sjoin);

		Map<String, String> map = new HashMap<>();

		// 소셜 참가 취소 메세지를 주최자에게 전달
		Social social = socialService.getSpayInfo(sjoin.getSno());
		memberService.sendAlarm(social.getMid(),
				sjoin.getMid() + "가 " + "\"" + social.getStitle() + "\"" + " 어셈블 참가를 취소했습니다. :(\n");

		map.put("response", "success");

		return map;
	}

	// 소셜 참가 히스토리
	@GetMapping("/history/join")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Map<String, Object> joinHistory(@RequestParam(defaultValue = "1") int jPageNo, String mid) {
		// 소셜 참가 수 가져오기
		int totalRows = socialService.getJoinHistoryCnt(mid);
		Pager pager = new Pager(4, 5, totalRows, jPageNo);

		Map<String, Object> param = new HashMap<>();
		param.put("mid", mid);
		param.put("pager", pager);

		// 소셜 참가 히스토리 가져오기
		List<Social> joinHistory = socialService.getJoinHistory(param);

		Map<String, Object> map = new HashMap<>();
		map.put("response", "success");
		map.put("joinHistory", joinHistory);
		map.put("pager", pager);

		return map;
	}

	// 소셜 모집 히스토리
	@GetMapping("/history/recruit")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Map<String, Object> recruitHistory(@RequestParam(defaultValue = "1") int rPageNo, String mid) {
		// 소셜 모집 수 가져오기
		int totalRows = socialService.getRecruitHistoryCnt(mid);
		Pager pager = new Pager(4, 5, totalRows, rPageNo);

		Map<String, Object> param = new HashMap<>();
		param.put("mid", mid);
		param.put("pager", pager);

		// 소셜 모집 가져오기
		List<Social> recruitHistory = socialService.getRecruitHistory(param);

		Map<String, Object> map = new HashMap<>();
		map.put("response", "success");
		map.put("recruitHistory", recruitHistory);
		map.put("pager", pager);

		return map;
	}

	@GetMapping("/applyAssemble")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public List<Social> applyAssemble(String mid) {
		List<Social> socialList = socialService.getApplyAssemble(mid);
		return socialList;
	}

	// 소셜 참가 인원 리스트
	@GetMapping("/sjoin/list/{sno}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Map<String, Object> joinMemberList(@PathVariable int sno) {
		// 소셜에 참가한 사용자 데이터 가져오기
		List<Member> memberList = memberService.getJoinMember(sno);

		Map<String, Object> map = new HashMap<>();
		map.put("response", "success");
		map.put("memberList", memberList);

		return map;
	}

	// 소셜 마감 여부 확인
	@GetMapping("/deadline/{sno}")
	public Map<String, Object> isDeadline(@PathVariable int sno) {
		// 소셜 마감 여부 가져오기
		boolean isDeadline = socialService.isDeadline(sno);

		Map<String, Object> map = new HashMap<>();
		map.put("response", "success");
		map.put("isDeadline", isDeadline);

		return map;
	}

	// 주최자가 사용자 참가 거부
	@DeleteMapping("/sjoin/refuse")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Map<String, Object> refuseJoinMember(Sjoin sjoin) {
		// 소셜 참가 거부
		socialService.cancelSjoin(sjoin);

		Map<String, Object> map = new HashMap<>();

		// 사용자에게 소셜 참가 거부 메시지 전달
		Social social = socialService.getSpayInfo(sjoin.getSno());
		memberService.sendAlarm(sjoin.getMid(), "\"" + social.getStitle() + "\"" + " 호스트가 어셈블 참가를 거절했습니다. :(\n"
				+ "환불된 금액: " + String.format("%,d", social.getSfee()) + "원");
		map.put("response", "success");

		return map;
	}

	// 검색기능
	@GetMapping("/search")
	public Map<String, Object> searchList(String keyword, @RequestParam(defaultValue = "1") int pageNo) {
		// 키워드로 검색한 소셜의 수 가져오기
		int totalRows = socialService.getSocialCntByKeyword(keyword);

		// 페이징 처리
		Pager pager = new Pager(9, 5, totalRows, pageNo);

		Map<String, Object> param = new HashMap<>();
		param.put("keyword", keyword);
		param.put("pager", pager);

		// 키워드로 검색한 소셜 리스트 가져오기
		List<Social> list = socialService.getSearchList(param);

		// 검색결과를 담을 맵 생성
		Map<String, Object> map = new HashMap<>();

		// 데이터를 맵에 저장
		map.put("response", "success");
		map.put("keyword", keyword);
		map.put("searchSocialList", list);
		map.put("pager", pager);

		return map;
	}

	@GetMapping("/mainCateSocial")
	public List<Social> mainCateSocial(String mid) {
		List<Social> socialList = socialService.getMainCateSocial(mid);
		return socialList;
	}

	@GetMapping("/recruitAssemble")
	public List<Social> recruit(String mid) {
		List<Social> socialList = socialService.getRecruitAssemble(mid);
		return socialList;
	}

	@GetMapping("/recruitedAssemble")
	public List<Social> recruited(String mid) {
		List<Social> socialList = socialService.getRecruitedAssemble(mid);
		return socialList;
	}

	@GetMapping("/inprogress")
	public Map<String, Object> inprogress(String mid, @RequestParam(defaultValue = "1") int pageNo,
			@RequestParam(defaultValue = "0") int ctno, String sort) {
		Map<String, Object> param = new HashMap<>();
		param.put("mid", mid);
		if (ctno != 0) {
			param.put("ctno", ctno);
		}

		int totalRows = socialService.getInprogressCnt(param);
		if (sort != null) {
			param.put("sort", sort);
		}
		Pager pager = new Pager(9, 5, totalRows, pageNo);
		param.put("pager", pager);

		List<Social> socialList = socialService.getInprogress(param);

		Map<String, Object> map = new HashMap<>();
		map.put("socialList", socialList);
		map.put("pager", pager);

		return map;
	}

	@GetMapping("/progressed")
	public Map<String, Object> progressed(String mid, @RequestParam(defaultValue = "1") int pageNo,
			@RequestParam(defaultValue = "0") int ctno, String sort) {
		Map<String, Object> param = new HashMap<>();
		param.put("mid", mid);
		if (ctno != 0) {
			param.put("ctno", ctno);
		}

		int totalRows = socialService.getProgessedCnt(param);
		if (sort != null) {
			param.put("sort", sort);
		}

		Pager pager = new Pager(9, 5, totalRows, pageNo);
		param.put("pager", pager);

		List<Social> socialList = socialService.getProgressed(param);

		Map<String, Object> map = new HashMap<>();
		map.put("socialList", socialList);
		map.put("pager", pager);

		return map;
	}

}
