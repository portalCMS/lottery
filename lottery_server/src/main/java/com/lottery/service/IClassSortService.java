package com.lottery.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.ClassSort;
import com.lottery.bean.entity.vo.ClassSortVO;

@Service
public interface IClassSortService {
	/**
	 * 根据类型查询显示对应的已添加的分类信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<ClassSortVO> findClassSorts(final Map<String,Object> param)throws Exception;
	/**
	 * 保存帮助中心分类
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public ClassSort saveHelpCenterSort(final Map<String, Object> param)throws Exception;
	/**
	 * 更新帮助中心分类
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public ClassSort updateHelpCenterSort(final Map<String, Object> param)throws Exception;
	/**
	 * 根据参数查询对应的分类信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<ClassSort> findClassSortsbyother(final Map<String,Object> param)throws Exception;
	/**
	 * 删除帮助中心分类
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public ClassSort deleteHelpCenterSort(final Map<String, Object> param)throws Exception;
	/**
	 * 前台帮助中心类型查询
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<ClassSort> findClassSortsbyWebApp(final Map<String,Object> param)throws Exception;
}
