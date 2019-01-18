package com.lottery.job;

import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.lottery.api.entity.SendMessage;
import com.lottery.bean.entity.vo.TempMapVO;
import com.lottery.dao.IAdminParameterDao;
import com.lottery.service.IAdminParameterService;
import com.lottery.service.IBetRecordService;
import com.xl.lottery.GrabNo.ClientFactory;
import com.xl.lottery.GrabNo.LotteryVo;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.exception.LotteryExceptionLog;
import com.xl.lottery.util.ApplicationContextUtil;
import com.xl.lottery.util.JsonUtil;

/**
 * 将最新的开奖结果发送到前台的map中子线程
 * @author swim
 *2014-12-12
 */
public class RefreshOpenNumResult implements Runnable {
	
	private IAdminParameterService parameterService
			=(IAdminParameterService) ApplicationContextUtil.getBean("adminParameterServiceImpl");
	
	private LotteryVo lotVo;

	public RefreshOpenNumResult(LotteryVo lotVo) {
		this.lotVo = lotVo;
	}
	@Override
	public void run() {
		String[] keys = new String[]{"webApp"};
		String url = "";
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("parameterName","webUrl");
			param.put("parameterKeys",keys);
			Map<String,String> map = parameterService.getParameterList(param);
			url = map.get("webApp");
		} catch (Exception e1) {
			LotteryExceptionLog.wirteLog(e1);
			return;
		}
		String links = url+"/refreshOpenNumResult.shtml";
		TempMapVO mapVo = new TempMapVO();
		mapVo.setKey(lotVo.getLotteryGroup()+"-"+lotVo.getLotteryCode());
		mapVo.setValue(lotVo.getExpectStr());
		mapVo.setValue2(lotVo.getNum());
		String params = JsonUtil.objToJson(mapVo);
		params = Base64.encodeBase64String(params.getBytes());
		//将最新的开奖结果发送到前台的map中
		String jsonStr;  
		for(int i=0;i<10;i++){
			try {
				jsonStr = ClientFactory.getUrl(links,"msg="+params);
				if(!StringUtils.isEmpty(jsonStr)&&jsonStr.equals("success")){
					break;
				}
			} catch (Exception e) {
				continue;
			}
		}
			
	}

}
