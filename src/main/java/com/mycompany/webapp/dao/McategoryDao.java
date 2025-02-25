package com.mycompany.webapp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.Mcategory;

@Mapper
public interface McategoryDao {

	List<Mcategory> getMcategory(String mid);

	public void putMcategory(Mcategory mcategory);

	public int updateMcategory(Mcategory mcategory);

	public void deleteMcategory(String mid);

}
