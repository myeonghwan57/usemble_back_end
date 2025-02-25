package com.mycompany.webapp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.Notice;
import com.mycompany.webapp.dto.Pager;

@Mapper
public interface NoticeDao {

	public int insert(Notice notice);

	public int countAll();

	public List<Notice> selectByPage(Pager pager);

	public Notice selectNoticeByNno(int nno);

	public int updateByNno(Notice notice);

	public int deleteByNno(int nno);

	public List<Notice> noticeByPage(Map<String, Object> param);

	public List<Notice> getAllNotices();

	public Notice selectNoticeDetail(int nno);

	public List<Notice> getNotice();
}
