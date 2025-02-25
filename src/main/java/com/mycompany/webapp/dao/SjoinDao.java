package com.mycompany.webapp.dao;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.Sjoin;

@Mapper
public interface SjoinDao {

	public int insert(Sjoin sjoin);

	public int count(int sno);

	public boolean isSjoin(Sjoin sjoin);

	public int deleteSjoin(Sjoin sjoin);

}
