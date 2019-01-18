package com.lottery.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.PlayModel;
import com.lottery.bean.entity.vo.PlayModelVO;

@Service
public interface IPlayModelService {

	/**
	 * 获取所有玩法信息(需添加查询条件)
	 * 
	 * @param param
	 * @return
	 */
	public List<PlayModel> queryPlayModel(final Map<String, Object> param);

	/**
	 * 获取所有玩法信息根据彩种组CODE
	 * 
	 * @param param
	 * @return
	 */
	public List<PlayModel> queryPlayModelByGroupCode(final Map<String, ?> param) throws Exception;

	/**
	 * 获取所有玩法信息根据彩种CODE
	 * 
	 * @param param
	 * @return
	 */
	public List<PlayModel> queryPlayModelByLotteryCode(final Map<String, ?> param) throws Exception;

	/**
	 * 获取所有玩法信息(无条件，获取所有信息)
	 * 
	 * @param param
	 * @return
	 */
	public List<PlayModel> getAllPlayModel() throws Exception;

	List<PlayModelVO> queryPlayModelVoByLotteryCode(Map<String, ?> param) throws Exception;
}
