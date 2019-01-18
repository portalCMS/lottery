package com.lottery.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.VCustomerFrozenUser;
import com.lottery.bean.entity.vo.VCustomerFrozenUserVO;
import com.lottery.dao.VCustomerFrozenUserDao;
import com.lottery.persist.generice.GenericDAO;
import com.xl.lottery.util.DateUtil;

@Repository
public class VCustomerFrozenUserDaoImpl extends GenericDAO<VCustomerFrozenUser>
		implements VCustomerFrozenUserDao {

	public VCustomerFrozenUserDaoImpl() {
		super(VCustomerFrozenUser.class);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public Page<VCustomerFrozenUserVO, VCustomerFrozenUser> findVCustomerFrozenUsers(
			Map<String, ?> param) throws Exception {
		// TODO Auto-generated method stub
		VCustomerFrozenUserVO vo = (VCustomerFrozenUserVO) param
				.get("frozenvokey");
		List<String> formula = new ArrayList<String>();
		List<String> limitKeys = new ArrayList<String>();
		List<Object> limitVals = new ArrayList<Object>();
		if (vo.getCustomerName() != null && vo.getCustomerName() != "") {
			limitKeys.add("customerName");
			formula.add("like");
			limitVals.add(vo.getCustomerName() + "%");
		}
		if (vo.getFrozenBeginTime() != null && vo.getFrozenBeginTime() != "") {
			limitKeys.add("updateTime");
			formula.add(">=");
			limitVals.add(DateUtil.strToDateLong(vo.getFrozenBeginTime()));
		}else{
			limitKeys.add("updateTime");
			formula.add(">=");
			limitVals.add(DateUtil.strToDateLong(DateUtil.getNextLongDay(DateUtil.getStringDate(), "-7")));
		}
		if (vo.getFrozenEndTime() != null && vo.getFrozenEndTime() != "") {
			limitKeys.add("updateTime");
			formula.add("<=");
			limitVals.add(DateUtil.strToDateLong(DateUtil.getStringDate()));
		}else{
			limitKeys.add("updateTime");
			formula.add("<=");
			limitVals.add(DateUtil.strToDateLong(DateUtil.getStringDate()));
		}
		Page<VCustomerFrozenUserVO, VCustomerFrozenUser> page = (Page<VCustomerFrozenUserVO, VCustomerFrozenUser>) doPageQuery(
				vo, VCustomerFrozenUser.class, formula, limitKeys, limitVals,true);
		return page;
	}

}
