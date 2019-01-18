package com.lottery.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.BonusGroup;

@Service
public interface IBonusGroupService {
	/**
	 * 查询所有有效的奖金组配置
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<BonusGroup> findBonusGroupAll(final Map<String,?> param)throws Exception;
	/**
	 * 保存奖金组配置
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String saveBonusGroups(final Map<String,?> param)throws Exception;
	/**
	 * 更新奖金组配置
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String updateBonusGroups(final Map<String,?> param)throws Exception;
	/**
	 * 通过id查询奖金组
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public BonusGroup findBonusGroupById(final Map<String,?> param)throws Exception;
}
