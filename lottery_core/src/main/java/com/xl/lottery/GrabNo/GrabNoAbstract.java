package com.xl.lottery.GrabNo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



public abstract class GrabNoAbstract {

	public static Map<String,String> lotteryTypeMap = new HashMap<String, String>();
	
	static{
		lotteryTypeMap.put("1", "com.xl.lottery.GrabNo.GoodTypeGrabNO");                        //不同具体彩种的不同抓取实现类
	}
	
	public static Map<String, List<LotteryVo>> getGrabNO(final Map<String,Object> param) throws Exception{
		String typeKey = (String) param.get("typeKey");
		String clzName =  GrabNoAbstract.lotteryTypeMap.get(typeKey);
		GrabNoAbstract grabNo =  (GrabNoAbstract) Class.forName(clzName).newInstance();
		return grabNo.invoke(param);
	}
	
	public abstract Map<String, List<LotteryVo>> invoke(final Map<String,Object> param)throws Exception;
	
	public static void main(String[] args) {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("typeKey", "1");
			param.put("url", "http://www.cailele.com/static/sh11x5/newlyopenlist.xml;?gameEn=shd11?");
			param.put("typeCode", "ssc");
			param.put("lotteryType", "shssc");
			param.put("minusFlag", false);
			Map<String, List<LotteryVo>> list = GrabNoAbstract.getGrabNO(param);
			System.out.println(list.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
