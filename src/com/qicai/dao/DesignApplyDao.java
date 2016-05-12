package com.qicai.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qicai.bean.minisite.DesignApply;
import com.qicai.dto.PageDTO;

public interface DesignApplyDao {
	void save(@Param("apply")DesignApply apply);//��
	void update(@Param("apply")DesignApply apply);//��
	DesignApply getByParam(@Param("apply")DesignApply apply);//����������
	List<DesignApply> getListByParam(@Param("apply")DesignApply apply);//��ѯ����
	List<DesignApply> getListByPage(@Param("page")PageDTO<DesignApply> page);//��ѯ����
	int getCountByParam(@Param("apply")DesignApply apply);//��ѯ����
}
