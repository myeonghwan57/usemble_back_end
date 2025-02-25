package com.mycompany.webapp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.Alarm;

@Mapper
public interface AlarmDao {
	public int insert(Alarm alarm);

	public List<Alarm> selectAlarmByPager(Map<String, Object> param);

	public int countAlarmByMid(String mid);

	public void updateAlarmByAno(int ano);

	public boolean getAlarmStateByMid(String mid);
}
