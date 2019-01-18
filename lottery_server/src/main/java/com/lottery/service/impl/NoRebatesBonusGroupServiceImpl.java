package com.lottery.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.lottery.bean.entity.NoRebatesBonusGroup;
import com.lottery.bean.entity.vo.NoRebatesBonusGroupVO;
import com.lottery.dao.INoRebatesBonusGroupDao;
import com.lottery.service.INoRebatesBonusGroupService;
import com.xl.lottery.util.BeanPropertiesCopy;

@Service
public class NoRebatesBonusGroupServiceImpl implements INoRebatesBonusGroupService {

	@Autowired
	private INoRebatesBonusGroupDao noRebatesBonusGroupDao;

	@Override
	public List<NoRebatesBonusGroupVO> findNoRebatesBonusGroups(
			Map<String, ?> param) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer queryString  = new StringBuffer(" from NoRebatesBonusGroup t where t.bonusGroupId=? and t.status = 10002");
		Query query = noRebatesBonusGroupDao.getSession().createQuery(queryString.toString());
		query.setParameter(0, Long.parseLong(param.get("id").toString()));
		List<NoRebatesBonusGroup> entitys = query.list();
		List<NoRebatesBonusGroupVO> vos = new ArrayList<NoRebatesBonusGroupVO>();
		for(NoRebatesBonusGroup entity:entitys){
			NoRebatesBonusGroupVO vo = new NoRebatesBonusGroupVO();
			BeanUtils.copyProperties(vo, entity);
			vos.add(vo);
		}
		return vos;
	}
}
