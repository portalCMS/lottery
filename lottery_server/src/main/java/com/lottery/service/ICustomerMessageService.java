package com.lottery.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.CustomerMessage;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.CustomerMessageVO;

@Service
public interface ICustomerMessageService {

	/**
	 * 获取自己的msg未读数量
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Integer getMsgCount(Map<String, Object> param)throws Exception;

	public Page<CustomerMessageVO, CustomerMessage> queryMsgPage(Map<String, Object> param)throws Exception;
	
	/**
	 * 后台发送msg
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String saveMsg(Map<String,Object> param)throws Exception;
	
	/**
	 * 前台发送MSG
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String saveWebMsg(Map<String, Object> param)throws Exception;
	
	
	/**
	 * 标记已读
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String updateReadMsg(long id,CustomerUser user)throws Exception;
	
	
	/**
	 * 删除
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String deleteMsg(long id,CustomerUser user)throws Exception;
}
