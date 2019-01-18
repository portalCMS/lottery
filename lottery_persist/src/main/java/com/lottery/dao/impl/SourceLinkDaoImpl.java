package com.lottery.dao.impl;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.LotteryPlayBonus;
import com.lottery.bean.entity.PlayModel;
import com.lottery.bean.entity.SourceLink;
import com.lottery.dao.ILotteryPlayBonusDao;
import com.lottery.dao.IPlayModelDao;
import com.lottery.dao.ISourceLinkDao;
import com.lottery.persist.generice.GenericDAO;

@Repository
public class SourceLinkDaoImpl extends GenericDAO<SourceLink> implements ISourceLinkDao{

	public SourceLinkDaoImpl() {
		super(SourceLink.class);
	}


}
