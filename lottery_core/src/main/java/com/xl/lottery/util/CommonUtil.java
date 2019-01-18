package com.xl.lottery.util;

import java.util.HashMap;
import java.util.Map;

public class CommonUtil {

	/**
	 * 系统统一使用获取后台用户KEY
	 */
	public static final String USERKEY = "userkey";

	/**
	 * 系统统一使用获取前台用户KEY
	 */
	public static final String CUSTOMERUSERKEY = "customerkey";

	/**
	 * ServletContext中权限Key
	 */
	public static final String ROLEKEY = "rolekey";

	/**
	 * 操作类型 新增
	 */
	public static final String SAVE = "save";

	/**
	 * 操作类型 查询
	 */
	public static final String SELECT = "select";

	/**
	 * 操作类型 删除
	 */
	public static final String DELETE = "delete";

	/**
	 * 操作类型 修改
	 */
	public static final String UPDATE = "update";

	/**
	 * 单点充值订单类型
	 */
	public static final String ORDER_DETAIL_SINGLE_RECHARGE = "SR";

	/**
	 * 单点提款订单类型
	 */
	public static final String ORDER_DETAIL_SINGLE_DRAWING = "SD";
	
	/**
	 * 单点充值订单类型
	 */
	public static final String ORDER_DETAIL_USER_RECHARGE = "UR";
	
	/**
	 * 用户提款订单类型
	 */
	public static final String ORDER_DETAIL_USER_DRAWING = "UD";
	
	/**
	 * 订单类型
	 */
	public static final String ORDER_DETAIL_BET_ORDER = "OO";
	
	/**
	 * 退款订单类型
	 */
	public static final String ORDER_DETAIL_CANCEL_ORDER = "RR";
	
	/**
	 * 资金转移
	 */
	public static final String ORDER_DETAIL_MOVE_MONRY = "MM";

	public static final String DEFAULT_TOKEN_NAME = "TOKEN";
	
	public static final String DEFAULT_LOTTERY_TASK_NAME= "com.lottery.job.LotteryTask";
	
	public static final String DEFAULT_LOTTERY_JOB_NAME= "com.lottery.job.LotteryJob";
	
	public static final String JOB_TYPE_LOTTERY="J";
	
	public static final String JOB_TYPE_SERIES="S";
	
	/**
	 * 普通
	 */
	public static final String TRACK_P = "P";
	
	/**
	 * 追号
	 */
	public static final String TRACK_Z = "Z";
	public static final String TRACK_L = "L";
	
	public static final String HELP_CENTER = "A1";
	
	public static final String NOTICE = "A2";
	
	public final static Map<String,String> lotteryGroupMap = new HashMap<String,String>() {{    
	    put("SYXW", "11选5");    
	    put("SSC", "时时彩");   
	    put("3D","3D/排3");
	}};
	
	public final static String LOTTERY_GROUP_SSC = "SSC";
	
	public final static String LOTTERY_GROUP_SYXW = "SYXW";
	
	public final static String LOTTERY_GROUP_3D = "3D";
	
	public final static String LOTTERY_GROUP_K3 = "K3";
	
	//广告位区域代码
	public static final String INDEX_CODE = "I_A";
	public static final String Notice_CODE = "N_A";
	
	//注册总代
	public static final String PROXYUSER = "regProxyUser";
	//注册链接开户
	public static final String LINKUSER = "linkUser";
	
	//第三方支付code
	//易宝
	public static final String PAYCODE_YB = "yb";
	//环讯
	public static final String PAYCODE_HX = "hx";
	//环讯key map
	public final static Map<String,String> hxKeyMap = new HashMap<String,String>() {{
		//http://cw.mp0594.com
	    put("029454", "53022934370230599645069046932247680992505058298069349289398509203265969614991085896299839150300574502300062538721360301342580931");    
	    //http://cw.ldteaing.com
	    put("029192", "32387429013997251956686326884898786871885385326079993234596063412511047916039199071599545227571297280493198620440698192297581043");   
	    //http://cw.sztubh.com
	    put("029191","20101121221363375363886844928408890456943860495131917123425619371247281932902535988659176702032359924885007512089073162217057880");
//	    put("029303", "11336588860116895137847892070814651847838987512940285947444451705555595730985038913052961349338718196634554405131543874269415539");    
	}};
	
	public final static Map<String,String> ybKeyMap = new HashMap<String,String>() {{
		put("10012421049", "Z9k24Z7c0m8kZpi4R283394ot0VnoU2V58733VXf65j5S732oeN35oKA26v4");
		put("10012421053", "UHK4D6JME7o49yBm6z1Bq0v03Y4Pp4ZzK22NE054Sn18zLd3lLJ4zPDfysZ6");
	}};
		
	//IMkey
	public static final String IM_KEY = "";
	
	/**
	 * 消息未读
	 */
	public static final Integer MSG_STATUS_UNREAD = 0;
	
	/**
	 * 消息已读
	 */
	public static final Integer MSG_STATUS_READ = 1;
	
	//彩票休市天数
	public static final int lotRestDay =0;
	//活动类型名称map
	public final static Map<String,String> actNameMap = new HashMap<String,String>() {{    
	    put("REG", "注册送活动");    
	    put("FRC", "首充送活动");   
	    put("TRC","充值送活动");
	    put("BET", "投注送活动");    
	    put("AWA", "中奖送活动");   
	    put("HEL","扶助送活动");
	    put("DEA","老用户回归送活动");
	}};
	//活动来源客户端
	public static final String ACTIVITY_SOURCE_CLIENT = "CLIENT";
	//活动来源网页
	public static final String ACTIVITY_SOURCE_WEB = "WEB";
	//活动来源无限制
	public static final String ACTIVITY_SOURCE_NONE = "NONE";
	
}
