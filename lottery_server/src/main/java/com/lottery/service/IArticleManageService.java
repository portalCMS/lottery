package com.lottery.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.ArticleManage;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.ArticleManageVO;

@Service
public interface IArticleManageService {
	/**
	 * 保存公告信息
	 * @param param
	 * @throws Exception
	 */
	public void saveNoticeArticle(final Map<String, Object> param)throws Exception;
	/**
	 * 查询并显示帮助中心信息
	 * @param param
	 * @throws Exception
	 */
	public Page<ArticleManageVO,ArticleManage> showHelpCenter(final Map<String, Object> param)throws Exception;
	/**
	 * 查询公告信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<ArticleManageVO, ArticleManage> queryNoticeArticle(final Map<String, Object> param)throws Exception;
	/**
	 * 更新公告
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String updateArticleManage(final Map<String,Object> param)throws Exception;
	/**
	 * 查询显示更新后的公告
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public ArticleManage showUpdateArticleMange(final Map<String,Object> param)throws Exception;
	/**
	 * 删除公告
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String deleteArticleManage(final Map<String,Object> param)throws Exception;
	/**
	 * 前台首页公告显示（默认4条）
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<ArticleManageVO> findDefaultNoticeArrticle(final Map<String,Object> param)throws Exception;
	/**
	 * 公告查询根据参数查询n条记录
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<ArticleManageVO> findNewNoticeArrticle(final Map<String,Object> param)throws Exception;
	/**
	 * 通过id查询公告
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public ArticleManageVO getNoticeArticleById(final Map<String,Object> param)throws Exception;

	/**
	 * 前台展示使用
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Page<ArticleManageVO, ArticleManage> queryWebAppNoticeArticle(final Map<String, Object> param)throws Exception;
	
	/**
	 * 帮助中心
	 */
	public List<ArticleManageVO> queryWebAppHelpCenterArticle(final Map<String, Object> param)throws Exception;
	
	/**
	 * 更新帮助评论
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String updateHelpArticle(final Map<String, Object> param)throws Exception;
}
