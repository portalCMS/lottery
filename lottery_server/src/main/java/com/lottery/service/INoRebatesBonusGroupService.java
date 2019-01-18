package com.lottery.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.vo.NoRebatesBonusGroupVO;


@Service
public interface INoRebatesBonusGroupService {

	/**
	 * 查询无返点返点组信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<NoRebatesBonusGroupVO> findNoRebatesBonusGroups(final Map<String,?> param)throws Exception;
}
