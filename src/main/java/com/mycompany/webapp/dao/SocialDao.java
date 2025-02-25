package com.mycompany.webapp.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.Social;

@Mapper
public interface SocialDao {
	public void insert(Social social);

	public Social selectBySno(int sno);

	public List<Social> selectSocialListByParams(Map<String, Object> param);

	public int countByCtno(Map<String, Object> param);

	public Social selectSthumbBySno(int sno);

	public void updateSstatusBySno(Map<String, Object> param);

	public List<Social> getMainSocial();

	public Social selectSpayInfoBySno(int sno);

	public List<Social> selectJoinHistoryByPager(Map<String, Object> param);

	public int selectJoinHistoryCnt(String mid);

	public int selectRecruitHistoryCnt(String mid);

	public List<Social> selectRecruitHistoryByPager(Map<String, Object> param);

	public List<Social> getApplyAssemble(String mid);

	public Date selectIsDeadlineBySno(int sno);

	public List<Social> selectSearchByStitle(Map<String, Object> param);

	public int selectByKeyword(String keyword);

	public List<Social> getMainCateSocial(String mid);

	public List<Social> selectByPage(Pager pager);

	public int countAll();

	public List<Social> getRecruitAssemble(String mid);

	public List<Social> getRecruitedAssemble(String mid);

	public List<Social> getInprogress(Map<String, Object> param);

	public List<Social> getProgressed(Map<String, Object> param);

	public int countInprogress(Map<String, Object> param);

	public int countProgressed(Map<String, Object> param);

	public int countAllByMid(String mid);

	public List<Map<String, Object>> selectRecruitInAdminByPager(Map<String, Object> param);

	public List<Map<String, Object>> selectJoinInAdminByPager(Map<String, Object> param);

	public int countSocialByKeyword(Map<String, Object> param);

	public List<Social> selectSocialByKeywordPage(Map<String, Object> param);

	public List<Social> selectAllSocial();

}
