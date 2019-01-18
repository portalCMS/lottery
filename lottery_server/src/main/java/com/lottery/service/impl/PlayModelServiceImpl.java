package com.lottery.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.PlayModel;
import com.lottery.bean.entity.vo.PlayModelVO;
import com.lottery.dao.IPlayAwardLevelDao;
import com.lottery.dao.IPlayModelDao;
import com.lottery.service.IPlayModelService;

@Service
public class PlayModelServiceImpl implements IPlayModelService {
	@Autowired
	private IPlayModelDao playDao;

	@Autowired
	private IPlayAwardLevelDao levelDao;

	@Override
	public List<PlayModel> queryPlayModel(Map<String, Object> param) {
		List<PlayModel> list = playDao.queryPlayModel(param);
		return list;
	}

	@Override
	public List<PlayModel> queryPlayModelByGroupCode(Map<String, ?> param) throws Exception {
		return playDao.queryPlayModelByGroupCode(param);
	}

	@Override
	public List<PlayModel> queryPlayModelByLotteryCode(Map<String, ?> param) throws Exception {
		return playDao.queryPlayModelByLotteryCode(param);
	}

	@Override
	public List<PlayModel> getAllPlayModel() throws Exception {
		List<PlayModel> playModels = playDao.queryAll();
		return playModels;
	}

	@Override
	public List<PlayModelVO> queryPlayModelVoByLotteryCode(Map<String, ?> param) throws Exception {
		List<PlayModelVO> playModelVOs = new ArrayList<PlayModelVO>();
		PlayModelVO playModelVO = null;
		for (PlayModel model : queryPlayModelByLotteryCode(param)) {
			playModelVO = new PlayModelVO();
			playModelVO.setModelCode(model.getModelCode());
			playModelVO.setModelName(model.getModelName());

			playModelVOs.add(playModelVO);
		}
		return playModelVOs;
	}
}
