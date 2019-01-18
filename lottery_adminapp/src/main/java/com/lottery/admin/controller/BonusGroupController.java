package com.lottery.admin.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.BonusGroup;
import com.lottery.bean.entity.vo.BonusGroupVO;
import com.lottery.bean.entity.vo.NoRebatesBonusGroupVO;
import com.lottery.service.IBonusGroupService;
import com.lottery.service.INoRebatesBonusGroupService;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.BeanPropertiesCopy;
import com.xl.lottery.util.CommonUtil;

@Controller
public class BonusGroupController extends BaseController{

	@Autowired
	private IBonusGroupService bonusGroupService;
	
	@Autowired
	private INoRebatesBonusGroupService noRebatesBonusGroupService;
	
	@RequestMapping("queryBonusGroups")
	public ModelAndView queryBonusGroups(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		List<BonusGroup> bonusGroups;
		request.getSession().removeAttribute("norebatesvokey");
		request.getSession().removeAttribute("bonusvokey");
		try {
			bonusGroups = bonusGroupService.findBonusGroupAll(param);
			model.put("bonusGroups", bonusGroups);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("money_group/groups",model);
	}
	
	@RequestMapping("showcreatebonus")
	public ModelAndView showCreateBonus(String isBack,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		if(isBack==null||isBack.equals("")){
			request.getSession().removeAttribute("norebatesvokey");
			request.getSession().removeAttribute("bonusvokey");
		}
		return new ModelAndView("money_group/create",model);
	}
	
	@RequestMapping("nextNoPoint")
	public ModelAndView nextNoPoint(BonusGroupVO bonusVO,HttpServletRequest request,HttpServletResponse response,RedirectAttributes attributes){
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			if(request.getSession().getAttribute("bonusvokey")==null||bonusVO.getRebates()!=null)request.getSession().setAttribute("bonusvokey", bonusVO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, attributes);
		}
		return new ModelAndView("money_group/noPoint",model);
	}
	
	@RequestMapping("nextDone")
	public ModelAndView nextDone(NoRebatesBonusGroupVO vo,HttpServletRequest request,HttpServletResponse response,RedirectAttributes attributes){
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			if(vo.getVos().size()>0)request.getSession().setAttribute("norebatesvokey", vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, attributes);
		}
		return new ModelAndView("money_group/done",model);
	}
	
	@RequestMapping("addBonusGroups")
	public ModelAndView saveBonusGroups(HttpServletRequest request,HttpServletResponse response,RedirectAttributes attributes){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		AdminUser user = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		HttpSession session = request.getSession();
		NoRebatesBonusGroupVO vo = (NoRebatesBonusGroupVO) session.getAttribute("norebatesvokey");
		BonusGroupVO bonusVO = (BonusGroupVO) session.getAttribute("bonusvokey");
		try {
			param.put("bonusvokey", bonusVO);
			param.put(CommonUtil.USERKEY, user);
			param.put("norebatesvokey", vo);
			bonusGroupService.saveBonusGroups(param);
			attributes.addFlashAttribute("success","保存成功");
			session.removeAttribute("norebatesvokey");
			session.removeAttribute("bonusvokey");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, attributes);
		}
		return new ModelAndView("redirect:queryBonusGroups.do",model);
	}
	
//	@RequestMapping("updateBonusGroups")
//	public ModelAndView updateBonusGroups(BonusGroupVO bonusVO,HttpServletRequest request,HttpServletResponse response,RedirectAttributes attributes){
//		Map<String, Object> model = new HashMap<String, Object>();
//		Map<String, Object> param = new HashMap<String, Object>();
//		AdminUser user = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
//		try {
//			param.put("bonusvokey", bonusVO);
//			param.put(CommonUtil.USERKEY, user);
//			bonusGroupService.updateBonusGroups(param);
//			attributes.addFlashAttribute("success","修改成功");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			LotteryExceptionLog.wirteLog(e, attributes);
//		}
//		return new ModelAndView("redirect:queryBonusGroups.do",model);
//	}
	
	@RequestMapping("showBonusGroup")
	public ModelAndView findBonusGroupById(BonusGroupVO bonusVO,HttpServletRequest request,HttpServletResponse response,RedirectAttributes attributes){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		session.removeAttribute("norebatesvokey");
		param.put("bonusvokey", bonusVO);
		try {
			if(session.getAttribute("bonusvokey")==null){
				BonusGroup entity = bonusGroupService.findBonusGroupById(param);
				BeanUtils.copyProperties(bonusVO, entity);
				bonusVO.setTheoryBonus(new BigDecimal(2000.00));
				session.setAttribute("bonusvokey", bonusVO);
			}else{
				BonusGroupVO oldvo = (BonusGroupVO) session.getAttribute("bonusvokey");
				bonusVO.setId(oldvo.getId());
				BonusGroup entity = bonusGroupService.findBonusGroupById(param);
				if(oldvo.getTheoryBonus()!=null){
					bonusVO.setTheoryBonus(oldvo.getTheoryBonus());
				}else{
					bonusVO.setTheoryBonus(new BigDecimal(2000.00));
				}
				BeanUtils.copyProperties(bonusVO, entity);
				session.setAttribute("bonusvokey", bonusVO);
			}
			//model.put("bonusvokey", bonusVO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("money_group/update",model);
	}
	
	@RequestMapping("nextUpdateNoPoint")
	public ModelAndView nextUpdateNoPoint(BonusGroupVO bonusVO,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		try {
			if(session.getAttribute("norebatesvokey")==null){
				BonusGroupVO oldbonusVO = (BonusGroupVO) session.getAttribute("bonusvokey");
				BeanPropertiesCopy.copyProperties(bonusVO, oldbonusVO);
				Long id = oldbonusVO.getId();
				param.put("id", id);
				List<NoRebatesBonusGroupVO> vos = noRebatesBonusGroupService.findNoRebatesBonusGroups(param);
				NoRebatesBonusGroupVO nrvo = new NoRebatesBonusGroupVO();
				nrvo.setVos(vos);
				session.setAttribute("bonusvokey", oldbonusVO);
				session.setAttribute("norebatesvokey",nrvo);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("money_group/updatenoPoint",model);
	}
	
	
	@RequestMapping("updatenextDone")
	public ModelAndView updatenextDone(NoRebatesBonusGroupVO vo,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			NoRebatesBonusGroupVO nrvo = (NoRebatesBonusGroupVO) request.getSession().getAttribute("norebatesvokey");
			List<NoRebatesBonusGroupVO> vos = nrvo.getVos();
			List<NoRebatesBonusGroupVO> newdata = vo.getVos();
			if(newdata != null&&newdata.size()!=0){
				vos.clear();
				vos.addAll(newdata);
			}else{
				for(int i=0;i<vos.size();i++){
					for(int j=0;j<newdata.size();j++){
						if(vos.get(i).getRebates().doubleValue()==newdata.get(j).getRebates().doubleValue()){
							vos.get(i).setBonus(newdata.get(j).getBonus());
						}
					}
				}
			}
			
			nrvo.setVos(vos);
			request.getSession().setAttribute("norebatesvokey",nrvo);
			//if(vo.getVos().size()>0)request.getSession().setAttribute("norebatesvokey", vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("money_group/updatedone",model);
	}
	
	
	@RequestMapping("updateBonusGroups")
	public ModelAndView updateBonusGroups(HttpServletRequest request,HttpServletResponse response,RedirectAttributes attributes){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		AdminUser user = (AdminUser) request.getSession().getAttribute(CommonUtil.USERKEY);
		HttpSession session = request.getSession();
		NoRebatesBonusGroupVO vo = (NoRebatesBonusGroupVO) session.getAttribute("norebatesvokey");
		BonusGroupVO bonusVO = (BonusGroupVO) session.getAttribute("bonusvokey");
		try {
			param.put("bonusvokey", bonusVO);
			param.put(CommonUtil.USERKEY, user);
			param.put("norebatesvokey", vo);
			bonusGroupService.updateBonusGroups(param);
			attributes.addFlashAttribute("success","修改成功");
			session.removeAttribute("norebatesvokey");
			session.removeAttribute("bonusvokey");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, attributes);
		}
		return new ModelAndView("redirect:queryBonusGroups.do",model);
	}
}
