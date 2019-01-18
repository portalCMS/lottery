package com.lottery.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.BankManage;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.BankManageVO;

@Service
public interface IBankManageService {
	
	/**
	 * 查询页数
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<BankManageVO, BankManage> findBankeManageList(final Map<String,?> param)throws Exception;
	/**
	 * 添加银行(工商，建设等)
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String saveBank(final Map<String,Object> param)throws Exception;
	/**
	 * 更新银行配置
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String updateBank(final Map<String,Object> param)throws Exception;
	/**
	 * 删除银行配置
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String deleteBank(final Map<String,Object> param)throws Exception;
	/**
	 * 查询所有银行配置记录
	 * @return
	 * @throws Exception
	 */
	public List<BankManage> findBankeManageList()throws Exception;
	/**
	 * 通过id查询对应银行配置
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public BankManage findBankById(final Map<String,Object> param)throws Exception;
	/**
	 * 统计已配置银行数量
	 * @return
	 * @throws Exception
	 */
	public int countBankManage() throws Exception;
	
	/**
	 * 查询有效的银行列表
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<BankManage> findBanks(final Map<String, Object> param)throws Exception;
	/**
	 * 查询可以进行绑定银行卡操作的银行
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<BankManage> findCanBindBankeList(final Map<String, Object> param)throws Exception;
	
}
