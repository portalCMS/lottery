package com.lottery.admin.controller;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lottery.bean.entity.AdminUser;
import com.lottery.bean.entity.CustomerIpLog;
import com.lottery.bean.entity.CustomerUser;
import com.lottery.bean.entity.Page;
import com.lottery.bean.entity.vo.AdminParameterVO;
import com.lottery.bean.entity.vo.CustomerIpLogVO;
import com.lottery.bean.entity.vo.CustomerUserVO;
import com.lottery.bean.entity.vo.ReportVO;
import com.lottery.bean.entity.vo.TempMapVO;
import com.lottery.filter.ExcelFile;
import com.lottery.service.IAdminParameterService;
import com.lottery.service.ICustomerIpLogService;
import com.lottery.service.ICustomerUserService;
import com.lottery.service.IReportService;
import com.lottery.service.IStatisticService;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DateUtil;
import com.xl.lottery.util.DownloadUtil;
import com.xl.lottery.util.EntityCopy;



@Controller
@RequestMapping("/statistic")
public class StatisticController extends BaseController {
	
	@Autowired
	private IStatisticService staService;
	
	@Autowired
	private ICustomerUserService userService;
	
	@Autowired
	private IAdminParameterService parameterService;
	
	@Autowired
	private ICustomerIpLogService ipService;
	
	@Autowired
	private IReportService reportService;
	
	@RequestMapping("/userStatistic")
	public ModelAndView showUserStatistic(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Map<String,Integer> userMap = userService.queryUserStatistic();
			model.putAll(userMap);
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
		}
		return new ModelAndView("stats/user/userQuery",model);
		
	}
	
	@RequestMapping("/queryMonthActiveUser")
	@ResponseBody
	public Map<String,?> queryMonthActiveUser(ReportVO vo ,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		if(null==vo.getStartTime()){
			vo.setStartTime(DateUtil.getCurrMonth()+"-01");
		}
		if(null==vo.getEndTime()){
			vo.setEndTime(DateUtil.getNextDay(DateUtil.getStringDateShort(), "1"));
		}else{
			vo.setEndTime(DateUtil.getNextDay(vo.getEndTime(), "1"));
		}
		param.put("reprotKey", vo);
		try {
			Map<String,String> userMap = userService.queryMonthActiveUser(param);
			
			int days = Integer.parseInt(userMap.get("days"));
			List<TempMapVO> maps = new ArrayList<TempMapVO>(days*2);
			int tmc = 0;
			int tpc = 0;
			for(int i=1;i<days;i++){
				TempMapVO temp1 = new TempMapVO();
				String key =DateUtil.getNextDay3(vo.getEndTime(), "-"+(i+1));
				if(null==userMap.get(key+"_p")){
					userMap.put(key+"_p", "0");
				}
				if(null==userMap.get(key+"_m")){
					userMap.put(key+"_m", "0");
				}
				int pc = Integer.parseInt(userMap.get(key+"_p"));
				int mc = Integer.parseInt(userMap.get(key+"_m"));
				temp1.setKey(key);
				temp1.setValue(String.valueOf(pc+mc));
				tmc+=mc;
				tpc+=pc;
				maps.add(temp1);
			}
			model.put("maps", maps);
			model.put("days", days);
			model.put("tmc", tmc);
			model.put("tpc", tpc);
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("/queryMonthAddUser")
	@ResponseBody
	public Map<String,?> queryMonthAddUser(ReportVO vo ,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		if(null==vo.getStartTime()){
			vo.setStartTime(DateUtil.getCurrMonth()+"-01");
		}
		if(null==vo.getEndTime()){
			vo.setEndTime(DateUtil.getNextDay(DateUtil.getStringDateShort(), "1"));
		}else{
			vo.setEndTime(DateUtil.getNextDay(vo.getEndTime(), "1"));
		}
		param.put("reprotKey", vo);
		try {
			Map<String,Integer> userMap = userService.queryMonthAddUser(param);
			
			int days = userMap.get("days");
			List<TempMapVO> maps1 = new ArrayList<TempMapVO>(days);
			int tc=0;
			int tpc=0;
			int tmc=0;
			for(int i=1;i<days;i++){
				TempMapVO temp1 = new TempMapVO();
				String key =DateUtil.getNextDay3(vo.getEndTime(), "-"+(i+1));
				if(null==userMap.get(key+"_p")){
					userMap.put(key+"_p", 0);
				}
				int pc =userMap.get(key+"_p");
				tc+=pc;
				temp1.setKey(key+"_p");
				temp1.setValue(pc+"");
				maps1.add(temp1);
			}
			tpc = tc;
			
			List<TempMapVO> maps2 = new ArrayList<TempMapVO>(days);
			for(int i=1;i<days;i++){
				TempMapVO temp1 = new TempMapVO();
				String key =DateUtil.getNextDay3(vo.getEndTime(), "-"+(i+1));
				if(null==userMap.get(key+"_m")){
					userMap.put(key+"_m", 0);
				}
				int mc =userMap.get(key+"_m");
				temp1.setKey(key+"_m");
				temp1.setValue(mc+"");
				maps2.add(temp1);
				tc+=mc;
			}
			tmc = tc-tpc;
			model.put("pMaps", maps1);
			model.put("mMaps", maps2);
			model.put("days", days);
			int stmc = userMap.get("mtsc")==null?0:userMap.get("mtsc");
			model.put("stmc", stmc);
			model.put("ptmc", tc-stmc);
			model.put("tc", tc);
			model.put("tpc", tpc);
			model.put("tmc", tmc);
			model.put("ltsc", userMap.get("ltsc"));
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	/**
	 * 下载用户活跃度报表
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/downloadUserExcel")
	public ModelAndView downloadUserExcel(ReportVO vo ,HttpServletRequest request, HttpServletResponse response){
		 String title = "用户活跃度统计";
		//查询用户数据
		Map<String, ?> dataMap = this.queryMonthActiveUser(vo, request, response);
		Map<String, Object> model = new HashMap<String, Object>();
		String path = request.getSession().getServletContext().getRealPath("/excel");
		// 默认测试数据
		String fileName = System.currentTimeMillis()+".xls";
        File file = new File(path+"/"+fileName);
        List<String> header = new ArrayList<String>();
        header.add( "日期");
        header.add("用户活跃度");
        List<List<String>> data = new ArrayList<List<String>>();
        List<TempMapVO> maps =  (List<TempMapVO>) dataMap.get("maps");
        int tmc = (Integer) dataMap.get("tmc");
        int tpc = (Integer) dataMap.get("tpc");
        for (int i = 0; i < maps.size(); ++i) {
        	TempMapVO temp = maps.get(i);
            List<String> inlist = new ArrayList<String>();
            inlist.add(temp.getKey());
            inlist.add(temp.getValue());
            data.add(inlist);
        }
        //加入总计信息
        List<String> inlist = new ArrayList<String>();
        inlist.add("总计：");
        inlist.add(String.valueOf(tmc+tpc));
        data.add(inlist);
        
        inlist = new ArrayList<String>();
        inlist.add(" ");
        inlist.add(" ");
        data.add(inlist);
        
        inlist = new ArrayList<String>();
        inlist.add("会员活跃度总计：");
        inlist.add(String.valueOf(tmc));
        data.add(inlist);
        
        inlist = new ArrayList<String>();
        inlist.add("代理活跃度总计：");
        inlist.add(String.valueOf(tpc));
        data.add(inlist);
       
        inlist = new ArrayList<String>();
        inlist.add("类型占比：");
        DecimalFormat df1 = new DecimalFormat("#####0.0");
		String percent_m = df1.format((tmc*0.01/((tmc+tpc)*0.01))*100);
		String percent_p = df1.format((tpc*0.01/((tmc+tpc)*0.01))*100);
        inlist.add("会员："+percent_m+"%   代理："+percent_p+"%");
        data.add(inlist);
        ExcelFile ef = new ExcelFile(title, header, data);
        try {
        	//保存文件
            ef.save(file);
            String downFileName = DateUtil.getStringDateShort()+"_userhot.xls";
            //下载文件
            DownloadUtil.downLoadFile(fileName,request, response, downFileName, "xls");
        } catch (Exception e) {
            e.printStackTrace();
            LotteryExceptionLog.wirteLog(e, model);
        }
		return null;
	}
	/**
	 * 下载新增用户报表
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/downloadAddUserExcel")
	public ModelAndView downloadAddUserExcel(ReportVO vo ,HttpServletRequest request, HttpServletResponse response){
		String title = "新增用户统计";
		//查询用户数据
		Map<String, ?> dataMap = this.queryMonthAddUser(vo, request, response);
		Map<String, Object> model = new HashMap<String, Object>();
		String path = request.getSession().getServletContext().getRealPath("/excel");
		// 默认测试数据
		String fileName = System.currentTimeMillis()+".xls";
        File file = new File(path+"/"+fileName);
        List<String> header = new ArrayList<String>();
        header.add( "日期");
        header.add("新增会员数");
        header.add("新增代理数");
        List<List<String>> data = new ArrayList<List<String>>();
        List<TempMapVO> pMaps = (List<TempMapVO>) dataMap.get("pMaps");
        List<TempMapVO> mMaps = (List<TempMapVO>) dataMap.get("mMaps");
        for(int i=0;i<mMaps.size();i++){
        	TempMapVO temp = mMaps.get(i);
        	TempMapVO temp2 = pMaps.get(i);
            String key = temp.getKey();
            List<String> inlist = new ArrayList<String>();
            inlist.add(key.substring(0,key.length()-2));
            inlist.add(temp.getValue());
            inlist.add(temp2.getValue());
            data.add(inlist);
        }
        
        List<String> inlist = new ArrayList<String>();
        int tpc = (Integer) dataMap.get("tpc");
        int tmc = (Integer) dataMap.get("tmc");
        inlist.add("总计：");
        inlist.add(String.valueOf(tmc));
        inlist.add(String.valueOf(tpc));
        data.add(inlist);
        
        inlist = new ArrayList<String>();
        inlist.add(" ");
        inlist.add(" ");
        data.add(inlist);
        
        inlist = new ArrayList<String>();
        int ptmc = (Integer) dataMap.get("ptmc");
        inlist.add("自主开户总计：");
        inlist.add(String.valueOf(ptmc));
        data.add(inlist);
        
        
        inlist = new ArrayList<String>();
        inlist.add("自主开户总计：");
        inlist.add("0");
        data.add(inlist);
      
        inlist = new ArrayList<String>();
        int stmc = (Integer) dataMap.get("stmc");
        inlist.add("自由注册总计：");
        inlist.add(String.valueOf(stmc));
        data.add(inlist);
        
        ExcelFile ef = new ExcelFile(title, header, data);
        try {
        	//保存文件
            ef.save(file);
            String downFileName = DateUtil.getStringDateShort()+"_useradd.xls";
            //下载文件
            DownloadUtil.downLoadFile(fileName,request, response, downFileName, "xls");
        } catch (Exception e) {
            e.printStackTrace();
            LotteryExceptionLog.wirteLog(e, model);
        }
		return null;
	}
	
	@RequestMapping("superquery")
	public ModelAndView showSuperQuery(HttpServletRequest request, HttpServletResponse response){
		//Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("stats/user/superQuery",model);
		
	}
	
	@RequestMapping("getSQData")
	@ResponseBody
	public Map<String,Object> getSuperQueryData(CustomerUserVO vo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		param.put("uservokey", vo);
		param.put("total", "1"); //开启统计
		param.put("totalStart", "SELECT COUNT(1),SUM(betMoney),SUM(withdrawals),SUM(recharge),SUM(cash),SUM(quotacount) FROM ( ");
		param.put("totalEnd", ") tt");
		try {
			Page<Object, Object> pageObj = staService.getSuperQueryData(param);
			String totalSql = pageObj.getRsvst1();
			param.put("totalSql",totalSql);
			model.put("data", pageObj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e,model);
		}
		return model;
	}
	
	@RequestMapping("downloadSuperQueryExcel")
	public void downloadSuperQueryExcel(CustomerUserVO vo,HttpServletRequest request, HttpServletResponse response){
		vo.setMaxCount(99999999);
		//查询用户数据
		Map<String, ?> dataMap = this.getSuperQueryData(vo, request, response);
				
		Map<String, Object> model = new HashMap<String, Object>();
		String path = request.getSession().getServletContext().getRealPath("/excel");
		// 默认测试数据
		File file = new File(path+"/saveExcel.xls");
		String title = "超级查询用户数据";
		List<String> header = new ArrayList<String>();
		header.add( "#");
		header.add("注册时间");
		header.add("昵称");
		header.add("账号");
		header.add("父级");
		header.add("类型");
		header.add("下级与配额");
		header.add("账户完善");
		header.add("投注量");
		header.add("充值量");
		header.add("提款量");
		header.add("账户余额");
		DecimalFormat df = new DecimalFormat("0.0"); 
		List<List<Object>> data = new ArrayList<List<Object>>();
		Page<Object, Object> pageObj =  (Page<Object, Object>) dataMap.get("data");
		for (int i = 0; i < pageObj.getEntitylist().size(); ++i) {
		    Object[] temp = (Object[]) pageObj.getEntitylist().get(i);
		    List<Object> inlist = new ArrayList<Object>();
		    inlist.add(i+1);
		    inlist.add(temp[15]);
		    inlist.add(temp[2]);
		    inlist.add(temp[3]);
		    inlist.add(temp[5]);
		    if(Integer.parseInt(temp[16].toString())==DataDictionaryUtil.CUSTOMER_TYPE_MEMBER){
		    	 inlist.add("会员["+df.format(Float.parseFloat(temp[17].toString())*100)+"%]");
		    }else{
		    	 inlist.add(com.xl.lottery.util.Util.transition(temp[6])+"级代理"+"["+df.format(Float.parseFloat(temp[17].toString())*100)+"%]");
		    }
		    inlist.add(temp[7]+"人/"+temp[8]+"个");
		    String sta = "";
		    if(Integer.parseInt(temp[9].toString())>0)sta+="绑卡";
		    if(Integer.parseInt(temp[10].toString())>0)sta+=" 分配卡";
		    inlist.add(sta);
		    inlist.add(temp[11]);
		    inlist.add(temp[12]);
		    inlist.add(temp[13]);
		    inlist.add(temp[14]);
		    data.add(inlist);
		}
		ExcelFile ef = new ExcelFile(title, header, data,true);
		try {
		   //保存文件
		   ef.objSave(file);
		   //下载文件
		  DownloadUtil.downLoadFile("saveExcel.xls", request,response, "d1.xls", "xls");
		} catch (Exception e) {
		    LotteryExceptionLog.wirteLog(e, model);
		}
	}
	
	@RequestMapping("profilequery")
	public ModelAndView showProfileQuery(ReportVO vo,HttpServletRequest request, HttpServletResponse response){
		vo.setPageNum(1);
		Map<String, Object> model =  this.queryBigUserData(vo);
		return new ModelAndView("stats/user/profileQuery",model);
		
	}
	
	private Map<String,Object> queryBigUserData(ReportVO vo){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		String[] keys = new String[]{"limitAmount","limitDays","reportDays"};
		param.put("parameterName", "bigUserConfig");
		param.put("parameterKeys", keys);
		Map<String,String> returnMap=null;
		try {
			returnMap = parameterService.getParameterList(param);
			BigDecimal limitAmount =  new BigDecimal(returnMap.get(keys[0]));
			String limitDays = returnMap.get(keys[1]);
			String reportDays = returnMap.get(keys[2]);
			if(StringUtils.isEmpty(vo.getRsvst1())||(vo.getStartTime()==null&&vo.getEndTime()==null)){
				vo.setStartTime(DateUtil.getNextDay(DateUtil.getStringDateShort(), "-"+limitDays)+" 00:04:00");
				vo.setEndTime(DateUtil.getNextDay(DateUtil.getStringDateShort(), "1")+" 00:04:00");
			}
			vo.setRsvdc2(limitAmount);
			param.put("reportKey", vo);
			Map<String,?> amountMap = userService.queryProfileAmount(param);
			List<TempMapVO> list = (List<TempMapVO>) amountMap.get("voList");
			List<TempMapVO> list2 = new ArrayList<TempMapVO>();
			List<TempMapVO> list3 = new ArrayList<TempMapVO>();
			for(int i=1;i<=list.size();i++){
				list2.add(list.get(i-1));
				if(i%3==0||i==list.size()){
					TempMapVO listVo = new TempMapVO();
					listVo.setVos((List<TempMapVO>) EntityCopy.copy(list2));
					list3.add(listVo);
					list2.clear();
				}
			}
			model.put("listVo",list3);
			model.put("listVo2",list);
			model.put("allUser", amountMap.get("allUser"));
			DecimalFormat df1 = new DecimalFormat("#####0.00");
			String amount = df1.format(limitAmount.doubleValue());
			model.put("limitAmount", amount);
			model.put("limitDays", limitDays);
			model.put("reportDays", reportDays);
		}catch(Exception e){
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	@RequestMapping("saveBigUserConfig")
	@ResponseBody
	public Map<String,?> saveBigUserConfig(AdminParameterVO vo,HttpServletRequest request, 
			HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Map<String,String> keyValueMap = new HashMap<String, String>();
			keyValueMap.put("limitDays", vo.getKey1());
			keyValueMap.put("limitAmount", vo.getKey2());
			keyValueMap.put("reportDays", vo.getKey3());
			param.put("keyValueMap", keyValueMap);
			param.put("parameterName", "bigUserConfig");
			param.put(CommonUtil.USERKEY, (AdminUser)request.getSession().getAttribute(CommonUtil.USERKEY));
			parameterService.saveParameterValue(param);
			model.put("successMsg", "保存成功!");
		} catch (Exception e) {
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("queryAmpuntPage")
	@ResponseBody
	public Map<String, Object> queryAmpuntPage(ReportVO vo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		String[] keys = new String[]{"limitAmount","limitDays","reportDays"};
		param.put("parameterName", "bigUserConfig");
		param.put("parameterKeys", keys);
		Map<String,String> returnMap=null;
		try {
			returnMap = parameterService.getParameterList(param);
			BigDecimal limitAmount =  new BigDecimal(returnMap.get(keys[0]));
			String limitDays = returnMap.get(keys[1]);
			vo.setStartTime(DateUtil.getNextDay(DateUtil.getStringDateShort(), "-"+limitDays)+" 00:00:00");
			vo.setEndTime(DateUtil.getStringDateShort()+" 23:59:59");
			vo.setRsvdc2(limitAmount);
			param.put("reportKey", vo);
			Map<String,?> amountMap = userService.queryProfileAmountMap(param);
			model.putAll(amountMap);
		}catch(Exception e){
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("queryProfileAmount")
	@ResponseBody
	public Map<String, Object> queryProfileAmount(ReportVO vo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		String[] keys = new String[]{"limitAmount","limitDays","reportDays"};
		param.put("parameterName", "bigUserConfig");
		param.put("parameterKeys", keys);
		Map<String,String> returnMap=null;
		try {
			returnMap = parameterService.getParameterList(param);
			BigDecimal limitAmount =  new BigDecimal(returnMap.get(keys[0]));
			vo.setRsvdc2(limitAmount);
			param.put("reportKey", vo);
			Map<String,?> amountMap = userService.queryProfileAmount(param);
			model.putAll(amountMap);
		}catch(Exception e){
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	/**
	 * 下载大户数据
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/downloadBigUserData")
	public ModelAndView downloadBigUserData(ReportVO vo ,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		String title = "大户数据统计";
		//查询用户数据
		vo.setRsvst1("time");
		Map<String, ?> dataMap = this.queryBigUserData(vo);
		List<TempMapVO> list = (List<TempMapVO>) dataMap.get("listVo2");
		String path = request.getSession().getServletContext().getRealPath("/excel");
		// 默认测试数据
		String fileName = System.currentTimeMillis()+".xls";
        File file = new File(path+"/"+fileName);
        List<String> header = new ArrayList<String>();
        header.add("数据项");
        header.add("数据值");
        List<List<String>> data = new ArrayList<List<String>>();
        for(TempMapVO tempVo : list){
        	 List<String> inlist = new ArrayList<String>();
             inlist.add(tempVo.getValue());
             inlist.add(tempVo.getValue2());
             data.add(inlist);
        }
        
        List<String> inlist = new ArrayList<String>();
        inlist.add("所有大户: ");
        inlist.add(String.valueOf(dataMap.get("allUser")));
        data.add(inlist);
        
        ExcelFile ef = new ExcelFile(title, header, data);
		try {
		   //保存文件
		   ef.save(file);
		   String downFileName = DateUtil.getStringDateShort()+"_bigUserData.xls";
		   //下载文件
		  DownloadUtil.downLoadFile(fileName, request,response, downFileName, "xls");
		} catch (Exception e) {
		    LotteryExceptionLog.wirteLog(e, model);
		}
		return null;
	}
	
	@RequestMapping("queryProfileData")
	@ResponseBody
	public Map<String, Object> queryProfileData(ReportVO vo,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			int limitDays = vo.getRsvdc1();
			vo.setStartTime(DateUtil.getNextDay(DateUtil.getStringDateShort(), "-"+limitDays)+" 00:04:00");
			vo.setEndTime(DateUtil.getNextDay(DateUtil.getStringDateShort(), "1")+" 00:04:00");
			param.put("reportKey", vo);
			Map<String,?> amountMap = userService.queryProfileData(param);
			model.putAll(amountMap);
		}catch(Exception e){
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("getIpLogs")
	@ResponseBody
	public Map<String, Object> getIpLogs(CustomerIpLogVO vo,HttpServletRequest request,
			HttpServletResponse reponse){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ipvo", vo);
		CustomerUser temp = new CustomerUser();
		temp.setId(vo.getId());
		param.put(CommonUtil.CUSTOMERUSERKEY,temp);
		try {
			Page<CustomerIpLogVO, CustomerIpLog> page = ipService.findIpLogs(param);
			model.put("page", page);
			model.put("totalCount", page.getTotalCount());
			model.put("pageNum", page.getPageNum());
			model.put("maxCount", page.getMaxCount());
			model.put("pageCount", page.getPageCount());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("/showMarketStasData")
	public ModelAndView showMarketStasData(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("stats/marketing/marketQuery");
	}
	
	@RequestMapping("queryMarketStasData")
	@ResponseBody
	public Map<String, Object> queryMarketStasData(ReportVO vo,HttpServletRequest request,
			HttpServletResponse reponse){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
//		vo.setEndTime(vo.getStartTime()+" 23:59:59");
//		vo.setStartTime(vo.getStartTime()+" 00:00:00");
		param.put("reportKey", vo);
		try {
			Map<String,?> marketMap = userService.queryMarketStatistic(param);
			model.putAll(marketMap);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("queryAvgPerStasData")
	@ResponseBody
	public Map<String, Object> queryAvgPerStasData(ReportVO vo,HttpServletRequest request,
			HttpServletResponse reponse){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		vo.setEndTime(DateUtil.getNextDay(DateUtil.getStringDateShort(), "1")+" 00:04:00");
		vo.setStartTime(DateUtil.getCurrMonth()+"-01 00:00:00");
		param.put("reportKey", vo);
		try {
			Map<String,?> avgMap = userService.queryAvgPerStasData(param);
			model.putAll(avgMap);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	@RequestMapping("queryAmountStasData")
	@ResponseBody
	public Map<String, Object> queryAmountStasData(ReportVO vo,HttpServletRequest request,
			HttpServletResponse reponse){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		vo.setEndTime(DateUtil.getNextDay(DateUtil.getStringDateShort(), "1")+" 00:04:00");
		vo.setStartTime(DateUtil.getCurrMonth()+"-01 00:00:00");
		param.put("reportKey", vo);
		try {
			Map<String,?> amountMap = userService.queryAmountStasData(param);
			model.putAll(amountMap);
		} catch (Exception e) {
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
	
	/**
	 * 下载运营常规数据
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/downloadMarketData")
	public ModelAndView downloadMarketData(ReportVO vo ,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		String title = "运营常规数据统计";
		//查询用户数据
		vo.setRsvst1("false");
		Map<String, ?> dataMap = this.queryMarketStasData(vo, request, response);
		List<TempMapVO> list = (List<TempMapVO>) dataMap.get("mapVos");
		String path = request.getSession().getServletContext().getRealPath("/excel");
		String fileName = System.currentTimeMillis()+".xls";
        File file = new File(path+"/"+fileName);
        List<String> header = new ArrayList<String>();
        header.add("数据项");
        header.add("数据值");
        List<List<String>> data = new ArrayList<List<String>>();
        for(TempMapVO tempVo : list){
        	 List<String> inlist = new ArrayList<String>();
             inlist.add(tempVo.getKey());
             inlist.add(tempVo.getValue());
             data.add(inlist);
        }
        
        ExcelFile ef = new ExcelFile(title, header, data);
		try {
		   //保存文件
		   ef.save(file);
		   String downFileName = DateUtil.getStringDateShort()+"_marketData.xls";
		   //下载文件
		  DownloadUtil.downLoadFile(fileName,request,response, downFileName, "xls");
		} catch (Exception e) {
		    LotteryExceptionLog.wirteLog(e, model);
		}
		return null;
	}
	
	/**
	 * 网站金额数据统计
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/downloadAmountExcel")
	public ModelAndView downloadAmountExcel(ReportVO vo ,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		String title = "网站金额数据统计";
		//查询用户数据
		Map<String, ?> dataMap = this.queryAmountStasData(vo, request, response);
		List<TempMapVO> list = (List<TempMapVO>) dataMap.get("amountVos");
		String path = request.getSession().getServletContext().getRealPath("/excel");
		String fileName = System.currentTimeMillis()+".xls";
        File file = new File(path+"/"+fileName);
        List<String> header = new ArrayList<String>();
        header.add("日期");
        header.add("投注总额");
        header.add("返奖总额");
        header.add("充值总额");
        header.add("提款总额");
        header.add("返点总额");
        header.add("网站余额");
        List<List<String>> data = new ArrayList<List<String>>();
        Compare cp = new Compare();
        Collections.sort(list, cp);
        for(TempMapVO tempVo : list){
        	 List<String> inlist = new ArrayList<String>();
             inlist.add(tempVo.getKey());
             inlist.add(tempVo.getVos().get(0).getValue());
             inlist.add(tempVo.getVos().get(1).getValue());
             inlist.add(tempVo.getVos().get(2).getValue());
             inlist.add(tempVo.getVos().get(3).getValue());
             inlist.add(tempVo.getVos().get(4).getValue());
             inlist.add(tempVo.getVos().get(5).getValue());
             data.add(inlist);
        }
        
        ExcelFile ef = new ExcelFile(title, header, data);
		try {
		   //保存文件
		   ef.save(file);
		   String downFileName = DateUtil.getStringDateShort()+"_amountData.xls";
		   //下载文件
		  DownloadUtil.downLoadFile(fileName,request,response, downFileName, "xls");
		} catch (Exception e) {
		    LotteryExceptionLog.wirteLog(e, model);
		}
		return null;
	}
	
	/**
	 * 内部list排序类，根据modelCode排序。
	 * @author CW-HP9
	 *
	 */
	class Compare implements Comparator<TempMapVO>{
			@Override
			public int compare(TempMapVO o1, TempMapVO o2) {
				int code1 = Integer.parseInt(o1.getKey());
				int code2 = Integer.parseInt(o2.getKey());
				if(code1>code2){
					return -1;
				}else if(code1<code2){
					return 1;
				}
				return 0;
			}
	}
	
	
	@RequestMapping("/showylreport")
	public ModelAndView showylreport(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("stats/marketing/ylreport");
	}
	
	@RequestMapping("queryykReport")
	@ResponseBody
	public Map<String, Object> queryykReport(ReportVO vo,HttpServletRequest request,HttpServletResponse reponse){
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("reportVO", vo);
		try {
			Page<Object, Object> page = reportService.queryYkReportAdmin(param);
			model.put("page", page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e, model);
		}
		return model;
	}
}
