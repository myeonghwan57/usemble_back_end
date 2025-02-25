package com.mycompany.webapp.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import com.mycompany.webapp.dto.Alarm;
import com.mycompany.webapp.dto.Category;
import com.mycompany.webapp.dto.Mcategory;
import com.mycompany.webapp.dto.Member;
import com.mycompany.webapp.dto.Mlike;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.security.AppUserDetails;
import com.mycompany.webapp.security.AppUserDetailsService;
import com.mycompany.webapp.security.JwtProvider;
import com.mycompany.webapp.service.MemberService;
import com.mycompany.webapp.service.ReviewService;
import com.mycompany.webapp.service.SocialService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/member")
public class MemberController {
	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private AppUserDetailsService userDetailsService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private SocialService socialService;

	@Autowired
	private ReviewService reviewService;

	@PostMapping("/login")
	public Map<String, String> userLogin(String mid, String mpassword) {
		Map<String, String> map = new HashMap<>();

		try {
			// 유저 정보 받아오기
			AppUserDetails userDetails = (AppUserDetails) userDetailsService.loadUserByUsername(mid);

			// 비밀번호 확인
			PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

			boolean checkResult = passwordEncoder.matches(mpassword, userDetails.getPassword());

			// 비밀번호가 같다면
			if (checkResult) {
				// 유저가 탈퇴한 계정일 때
				if (!userDetails.getMember().isMenabled()) {
					map.put("result", "fail");
					map.put("reason", "id");
				} else {
					// 유저 정보와 토큰을 맵에 넣어서 전달
					Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
							userDetails.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(authentication);

					String accessToken = jwtProvider.createAccessToken(mid, userDetails.getMember().getMrole());
					map.put("result", "success");
					map.put("mid", mid);
					map.put("mname", userDetails.getMember().getMname());
					map.put("mrole", userDetails.getMember().getMrole());
					map.put("accessToken", accessToken);
				}
			} else {
				map.put("result", "fail");
				map.put("reason", "password");
			}
		} catch (UsernameNotFoundException e) { // 존재하지 않는 계정일 때
			map.put("result", "fail");
			map.put("reason", "id");
			return map;
		}

		return map;
	}

	@PostMapping("/join")
	public Member join(Member member) {
		if (member.getMattach() != null && !member.getMattach().isEmpty()) {
			MultipartFile mf = member.getMattach();
			// 파일 이름 설정
			member.setMprofileName(mf.getOriginalFilename());
			// 파일 종류 설정
			member.setMprofileType(mf.getContentType());
			try {
				member.setMprofileData(mf.getBytes());
				mf.getInputStream().close();
			} catch (IOException e) {

			}
		}
		// 비밀번호
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder(); // 암호화된 비밀번호를 //
																										// 얻어낸다.
		member.setMpassword(passwordEncoder.encode(member.getMpassword()));
		log.info("" + member.isAgree());
		// 아이디 활성화 설정
		member.setMenabled(true);
		// 권한 설정
		member.setMrole("ROLE_USER");
		// 회원 가입 처리
		memberService.join(member);
		member.setMattach(null);
		member.setMprofileData(null);
		return member;
	}

	@GetMapping("/category")
	public List<Category> AllCategory() {
		List<Category> categoryList = memberService.getCategory();
		return categoryList;
	}

	@GetMapping("/likeList")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Map<String, Object> likeList(@RequestParam(defaultValue = "1") int pageNo, String mid) {
		// 내가 좋아요한 사람의 수
		int totalRows = memberService.getMyLikeCnt(mid);

		// 한 페이지에 4개의 열을 가지고 5개의 페이지 그룹을 가진 페이저 생성
		Pager pager = new Pager(4, 5, totalRows, pageNo);

		// 유저 아이디 기준으로 좋아요 페이지 가져오기
		Map<String, Object> param = new HashMap<>();
		param.put("mid", mid);
		param.put("pager", pager);
		List<String> likeList = memberService.getLikeList(param);

		// 좋아요 페이지와 페이저 전송
		Map<String, Object> map = new HashMap<>();
		map.put("response", "success");
		map.put("likeList", likeList);
		map.put("pager", pager);

		return map;
	}

	@GetMapping("/profile")
	public Map<String, Object> profile(String mid) {
		// 유저 아이디, 이름, 자기소개, 가입일 가져오기
		Member member = memberService.getProfile(mid);

		// 유저 정보 전송
		Map<String, Object> map = new HashMap<>();
		map.put("response", "success");
		map.put("member", member);

		return map;
	}

	@GetMapping("/privacy")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Member privacy(String mid) {
		// 유저 아이디, 이름, 생년월일, 전화번호, 성별, 은행명, 계좌번호, 동의사항 가져오기
		Member member = memberService.getPrivacy(mid);

		return member;
	}

	@GetMapping("/likeState")
	public boolean likeState(Mlike mlike) {
		// 유저 좋아요 여부 가져오기
		boolean likeState = memberService.getLikeState(mlike);

		return likeState;
	}

	@PostMapping("/like")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public void like(Mlike mlike) {
		// 좋아요 추가
		int like = memberService.like(mlike);
	}

	@DeleteMapping("/unlike")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public void unlike(Mlike mlike) {
		// 좋아요 삭제
		int like = memberService.deleteLike(mlike);

	}

	@GetMapping("/likeCnt")
	public int likeCnt(String mid) {
		// 좋아요 수 가져오기
		int likeCnt = memberService.getLikeCnt(mid);
		return likeCnt;
	}

	@GetMapping("/socialCnt")
	public int socialCnt(String mid) {
		// 소셜 작성 수 가져오기
		int socialCnt = socialService.getRecruitHistoryCnt(mid);
		return socialCnt;
	}

	@GetMapping("/reviewCnt")
	public int reviewCnt(String mid) {
		// 리뷰 받은 수 가져오기
		int reviewCnt = reviewService.getRecieveReviewCnt(mid);
		return reviewCnt;
	}

	@PatchMapping("/withdraw")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public void withdraw(String mid) {
		// 유저 탈퇴하기
		int withdraw = memberService.withdrawMember(mid);
	}

	@PatchMapping("/findPassword")
	public Map<String, String> findPassword(String mid) {
		Map<String, String> map = new HashMap<>();
		// 비밀번호 찾기(임시 비밀번호 부여)
		try {
			// 유저 정보 가져오기
			AppUserDetails userDetails = (AppUserDetails) userDetailsService.loadUserByUsername(mid);
			Member member = userDetails.getMember();

			// 임시 비밀번호 생성
			String newPassword = memberService.getTempPassword(member);

			// 임시 비밀번호
			map.put("result", "success");
			map.put("mpassword", newPassword);
		} catch (UsernameNotFoundException e) {
			// 아이디가 존재하지 않을 때
			map.put("result", "fail");
			map.put("reason", "id");
		}
		return map;
	}

	@GetMapping("/idCheck")
	public int idCheck(String mid) {
		int result = memberService.checkId(mid);
		return result;
	}

	@GetMapping("/mattach/{mid}")
	public void download(@PathVariable String mid, HttpServletResponse response) {
		Member member = memberService.getMemberAttach(mid);
		try {
			String fileName = new String(member.getMprofileName().getBytes("UTF-8"), "ISO-8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		} catch (UnsupportedEncodingException e) {

		}
		response.setContentType(member.getMprofileType());
		try {
			OutputStream os = response.getOutputStream();
			os.write(member.getMprofileData());
			os.flush();
			os.close();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	@PatchMapping("/updateProfile")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public void updateProfile(Member member) {
		// 프로필 이미지가 존재할 때
		if (member.getMattach() != null && !member.getMattach().isEmpty()) {
			// 멀티파트파일 데이터를 멤버 객체에 저장
			MultipartFile mf = member.getMattach();
			member.setMprofileName(mf.getOriginalFilename());
			member.setMprofileType(mf.getContentType());

			try {
				member.setMprofileData(mf.getBytes());
			} catch (IOException e) {

			}
		}

		// 멤버 객체로 프로필 업데이트
		memberService.updateProfile(member);
	}

	@PatchMapping("/updatePassword")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Map<String, String> updatePassword(String mid, String originPassword, String newPassword) {
		Map<String, String> map = new HashMap<>();

		// 유저 아이디 가져오기
		AppUserDetails userDetails = (AppUserDetails) userDetailsService.loadUserByUsername(mid);
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

		// 맴버 객체 생성 및 mid 설정
		Member member = new Member();
		member.setMid(mid);

		// 기존 비밀번호가 맞는지 확인
		boolean checkOrigin = passwordEncoder.matches(originPassword, userDetails.getPassword());

		// 기존 비밀번호가 맞다면
		if (checkOrigin) {
			// 신규 비밀번호가 기존 비밀번호와 동일한지 확인
			boolean checkResult = passwordEncoder.matches(newPassword, userDetails.getPassword());

			// 신규 비밀번호와 기존 비밀번호가 동일할 때
			if (checkResult) {
				map.put("response", "fail");
				map.put("reason", "newPassword");
			} else {
				// 새 비밀번호를 멤버 객체에 설정 후 비밀번호 업데이트
				member.setMpassword(passwordEncoder.encode(newPassword));
				memberService.updateMpassword(member);
				map.put("response", "success");
			}
		} else {
			map.put("response", "fail");
			map.put("reason", "originPassword");
		}

		return map;
	}

	@PatchMapping("/updatePrivacy")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Map<String, String> updatePrivacy(@RequestBody Member member) {
		// 새로운 멤버 정보로 업데이트
		memberService.updatePrivacy(member);

		Map<String, String> map = new HashMap<>();
		map.put("response", "success");

		return map;
	}

	@PatchMapping("/updateAgree")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Map<String, String> updateAgree(@RequestBody Member member) {
		// 선택 동의 사항 업데이트
		memberService.updateAgree(member);

		Map<String, String> map = new HashMap<>();
		map.put("response", "success");

		return map;
	}

	@GetMapping("/mcategory")
	public List<Mcategory> mcategoryList(String mid) {
		List<Mcategory> mcategory = memberService.getMcategory(mid);
		return mcategory;
	}

	@PostMapping("/putMcategory")
	public Map<String, String> putMcategory(@RequestBody List<Mcategory> mcategory) {
		memberService.putMcategory(mcategory);

		Map<String, String> map = new HashMap<>();
		map.put("response", "success");

		return map;
	}

	@PostMapping("/updateMcategory")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Map<String, String> updateMcategory(@RequestBody List<Mcategory> mcategory) {
		memberService.updateMcategory(mcategory);

		Map<String, String> map = new HashMap<>();
		map.put("response", "success");

		return map;
	}

	@DeleteMapping("/deleteMcategory")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public void deleteMcategory(String mid) {
		log.info(mid);
		memberService.deleteMcategory(mid);
	}

	@PostMapping("/putAgree")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Map<String, String> putAgree(String mid, boolean agree) {
		memberService.putAgree(mid);

		Map<String, String> map = new HashMap<>();
		map.put("response", "success");

		return map;
	}

	// 유저 알림 페이지 가져오기
	@GetMapping("/alarm")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Map<String, Object> getAlarm(@RequestParam(defaultValue = "1") int pageNo, String mid) {
		Map<String, Object> param = new HashMap<>();
		param.put("mid", mid);

		// 확인하지 않은 유저 알림 수 가져오기
		int totalRows = memberService.getAlarmCntByMid(mid);
		Pager pager = new Pager(4, 5, totalRows, pageNo);

		param.put("pager", pager);

		// 확인하지 않은 유저 알림 페이지 가져오기
		List<Alarm> alarmList = memberService.getAlarmList(param);

		Map<String, Object> map = new HashMap<>();
		map.put("response", "success");
		map.put("alarmList", alarmList);
		map.put("pager", pager);

		return map;
	}

	// 알림 체크
	@PatchMapping("/alarm/check")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Map<String, String> checkAlarm(int ano) {
		// 알림 체크 시 상태 변경
		memberService.checkAlarm(ano);

		Map<String, String> map = new HashMap<>();
		map.put("response", "success");

		return map;
	}

	// 확인하지 않은 알림 유무 확인
	@GetMapping("/alarm/state")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Map<String, Object> isAlarm(String mid) {
		// 확인하지 않은 알림 여부 가져오기
		boolean isAlarm = memberService.isAlarm(mid);

		Map<String, Object> map = new HashMap<>();
		map.put("response", "success");
		map.put("isAlarm", isAlarm);

		return map;
	}

}
