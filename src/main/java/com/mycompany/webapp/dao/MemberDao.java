package com.mycompany.webapp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.Member;
import com.mycompany.webapp.dto.Pager;

@Mapper
public interface MemberDao {
	public Member selectByMid(String mid);

	public Member selectOfficialProfileByMid(String mid);

	public int insert(Member member);

	public int withdrawMemberByMid(String mid);

	public int checkId(String mid);

	public Member attachSelectByMid(String mid);

	public int updateProfileByMid(Member member);

	public int updateMpasswordByMid(Member member);

	public int updatePrivacy(Member member);

	public Member selectPrivacyByMid(String mid);

	public int updateAgree(Member member);

	public int putAgree(String mid);

	public int countAll();

	public List<Member> selectByPage(Pager pager);

	public List<Member> getJoinMemberBySno(int sno);

	public int updateMemberEnableByMid(Member member);

	public Member selectMemberAdminByMid(String mid);

	public int countMemberByKeyword(Map<String, Object> param);

	public List<Member> selectMemberByKeywordPage(Map<String, Object> param);

	public List<Member> selectAllMemeber();

}
