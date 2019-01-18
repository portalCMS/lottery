package com.lottery.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.CustomerUrl;
import com.lottery.bean.entity.DomainUrl;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.DomainUrlVO;
import com.lottery.dao.IDomainUrlDao;
import com.lottery.dao.impl.AdminWriteLog;
import com.lottery.service.IDomainUrlService;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DateUtil;

@Service
public class DomainUrlServiceImpl implements IDomainUrlService{

	@Autowired
	private IDomainUrlDao domainUrlDao;
	
	@Autowired
	private AdminWriteLog adminWriteLog;
	
	@Override
	public Page<DomainUrlVO, DomainUrl> findDomainUrlList(Map<String, ?> param)
			throws Exception {
		// TODO Auto-generated method stub
		return domainUrlDao.findDomainUrlList(param);
	}

	@Override
	public String saveDomainUrl(Map<String, ?> param) throws Exception {
		// TODO Auto-generated method stub
		DomainUrl domainUrl = (DomainUrl) param.get("domainUrlkey");
		AdminUser adminUser = (AdminUser) param.get(CommonUtil.USERKEY);
		if(domainUrlDao.findDomainUrlList(domainUrl.getUrl().trim())==null){
			domainUrl.setStatus(DataDictionaryUtil.STATUS_OPEN);
			domainUrl.setCreateTime(DateUtil.getNowDate());
			domainUrl.setCreateUser(adminUser.getUserName());
			domainUrl.setUpdateTime(DateUtil.getNowDate());
			domainUrl.setUpdateUser(adminUser.getUserName());
			domainUrlDao.insert(domainUrl);
			adminWriteLog.saveWriteLog(adminUser, CommonUtil.SAVE,
						"DomainUrl", domainUrl.toString());

		}else{
			throw new LotteryException("操作失败此域名已存在");
		}
		return "success";
	}

	@Override
	public String updateDomainUrl(Map<String, ?> param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteDomainUrl(Map<String, ?> param) throws Exception {
		// TODO Auto-generated method stub
		DomainUrl domainUrl = (DomainUrl) param.get("domainUrlkey");
		AdminUser adminUser = (AdminUser) param.get(CommonUtil.USERKEY);
		DomainUrl entity = domainUrlDao.queryById(domainUrl.getId());
		entity.setStatus(DataDictionaryUtil.STATUS_CLOSE);
		try {
			domainUrlDao.update(entity);
			adminWriteLog.saveWriteLog(adminUser, CommonUtil.DELETE,
					"DomainUrl", entity.toString());
		} catch (Exception e) {
			// TODO: handle exception
			return "faile";
		}
		return "success";
	}

	@Override
	public List<DomainUrl> findDomainUrllist(Map<String, ?> param)
			throws Exception {
		// TODO Auto-generated method stub
		return domainUrlDao.findDomainUrllist(param);
	}

	@Override
	public List<DomainUrl> queryAll() throws Exception {
		// TODO Auto-generated method stub
		return domainUrlDao.queryAll();
	}

	
}
