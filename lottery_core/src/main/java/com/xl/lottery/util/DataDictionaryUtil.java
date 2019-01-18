package com.xl.lottery.util;


import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;


/**
 * 
 * @author CW-HP7
 *
 */
@Component
public class DataDictionaryUtil {

	private static Map<Long, String> dataString = new HashMap<Long, String>();
	
	public static Map<Long, String> getDataString(){
		return dataString;
	}
	
	public static final int COMMON_FLAG_0 = 0;
	public static final int COMMON_FLAG_1 = 1;
	public static final int COMMON_FLAG_2 = 2;
	
	/**
	 * 使用状态 停用(未启用,关闭)
	 */
	public static final int STATUS_CLOSE = 10001;
	
	/**
	 * 使用状态 启用
	 */
	public static final int STATUS_OPEN = 10002;
	
	/**
	 * 使用状态 删除(暂停销售)
	 */
	public static final int STATUS_DELETE = 10003;
	
	/**
	 * 就绪
	 */
	public static final int STATUS_READY = 10004;
	
	/**
	 * 执行成功
	 */
	public static final int STATUS_SUCCESS = 10005;
	
	/**
	 * 执行失败
	 */
	public static final int STATUS_FAILED = 10006;
	
	/**
	 * 执行中
	 */
	public static final int STATUS_RUNNING = 10007;
	
	/**
	 * 手动停止自动开奖
	 */
	public static final int HAND_STATUS_READY = 10010;
	
	/**
	 * 手动开奖执行中
	 */
	public static final int HAND_STATUS_RUNNING = 10011;
	
	/**
	 * 手动开奖失败
	 */
	public static final int HAND_STATUS_FAILED = 10012;
	
	/**
	 * 手动开奖成功
	 */
	public static final int HAND_STATUS_SUCCESS = 10013;
	
	/**
	 * 是否已经被激活 未激活
	 */
	public static final int STATUS_ONLINE_NO = 11001;
	
	/**
	 * 是否已经被激活 已激活
	 */
	public static final int STATUS_ONLINE_ON = 11002;
	
	/**
	 * 会员类型 代理
	 */
	public static final int CUSTOMER_TYPE_PROXY = 12001;
	
	/**
	 * 会员类型 会员
	 */
	public static final int CUSTOMER_TYPE_MEMBER = 12002;
	
	/**
	 * 会员类型 测试
	 */
	public static final int CUSTOMER_TYPE_TEST = 12003;
	
	/**
	 * 订单类型 收入
	 */
	public static final int ORDER_TYPE_INCOME = 14001;
	
	/**
	 * 订单类型 支出
	 */
	public static final int ORDER_TYPE_OUT = 14002;
	
	/**
	 * 继承状态 独立绑定
	 */
	public static final int EXTENDS_STATUS_NO = 15001;
	
	/**
	 * 继承状态 可继承
	 */
	public static final int EXTENDS_STATUS_OK = 15002;
	
	/**
	 * 授权状态 无需授权
	 */
	public static final int ROLE_PWD_SWITCH_NO = 16001;
	
	/**
	 * 授权状态 必须授权
	 */
	public static final int ROLE_PWD_SWITCH_YES = 16002;
	
	/**
	 * 订单状态 处理中
	 */
	public static final int ORDER_STATUS_DISPOSING = 17001;
	
	/**
	 * 订单状态 成功
	 */
	public static final int ORDER_STATUS_SUCCESS = 17002;
	
	/**
	 * 订单状态 失败
	 */
	public static final int ORDER_STATUS_FAILURE = 17003;
	
	/**
	 * 订单状态 过期
	 */
	public static final int ORDER_STATUS_OVERDUE = 17004;
	
	/**
	 * 订单状态 撤销
	 */
	public static final int ORDER_STATUS_CANCEL = 17005;
	
	/**
	 * 订单明细类型  普通投注扣款
	 */
	public static final int ORDER_DETAIL_ORDINARY_BETTING = 18001;
	
	
	/**
	 * 订单明细类型 追号投注冻结
	 */
	public static final int ORDER_DETAIL_CHASE_FREEZE = 18002;
	
	
	/**
	 * 订单明细类型 中奖返款
	 */
	public static final int ORDER_DETAIL_WINNING_REBATES = 18003;
	
	
	/**
	 * 订单明细类型 追号撤单返款
	 */
	public static final int ORDER_DETAIL_CHASE_AFTER_REBATES = 18004;
	
	/**
	 * 订单明细类型 投注返款
	 */
	public static final int ORDER_DETAIL_BETTING_REBATES = 18005;
	
	/**
	 * 订单明细类型 系统撤单返款
	 */
	public static final int ORDER_DETAIL_SYSTEM_REBATES = 18006;
	
	/**
	 * 订单明细类型 提款
	 */
	public static final int ORDER_DETAIL_WITHDRAWALS = 18007;
	
	/**
	 * 订单明细类型 后台扣款
	 */
	public static final int ORDER_DETAIL_BACKGROUND_DEDUCTIONS = 18008;
	
	/**
	 * 订单明细类型 现金充值
	 */
	public static final int ORDER_DETAIL_CASH_ADD = 18009;
	
	/**
	 * 订单明细类型 手续费返款
	 */
	public static final int ORDER_DETAIL_FEE_REFUND = 18010;
	
	/**
	 * 订单明细类型 后台充值
	 */
	public static final int ORDER_DETAIL_BACKGROUND_ADD = 18011;
	
	/**
	 * 订单明细类型 活动派发
	 */
	public static final int ORDER_DETAIL_DISTRIBUTED_ACTIVITIES = 18012;
	
	/**
	 * 订单明细类型 资金转出
	 */
	public static final int ORDER_DETAIL_MONEY_TRANSFER = 18013;
	
	/**
	 * 订单明细类型 资金转入
	 */
	public static final int ORDER_DETAIL_MONEY_INTO = 18014;
	
	/**
	 * 订单明细类型 投注盈收
	 */
	public static final int ORDER_DETAIL_BETTING_REVENUES = 18015;
	
	/**
	 * 订单明细类型 系统分红
	 */
	public static final int ORDER_DETAIL_SYSTEM_BONUS = 18016;
	
	/**
	 * 订单明细类型 追号订单
	 */
	public static final int ORDER_DETAIL_TRACK = 18017;
	
	/**
	 * 订单明细类型 撤单返款
	 */
	public static final int ORDER_DETAIL_CANCEL_STATUS = 18018;
	
	/**
	 *订单明细类型 第三方充值
	 */
	public static final int ORDER_DETAIL_OTHER_PAY = 18019;
	
	/**
	 * 权限类型 菜单
	 */
	public static final int PERMISSIONS_TYPE_MENU = 19001;
	
	/**
	 * 权限类型 URL
	 */
	public static final int PERMISSIONS_TYPE_URL = 19002;
	
	/**
	 * 投注单 状态
	 * 投注成功
	 */
	public static final int BET_ORDER_TYPE_SUCCESS = 21001;
	
	/**
	 * 投注单 状态
	 * 已中奖
	 */
	public static final int BET_ORDER_TYPE_WIN = 21002;
	
	/**
	 * 投注单 状态
	 * 未中奖
	 */
	public static final int BET_ORDER_TYPE_LOST = 21003;
	
	/**
	 * 投注单 状态
	 * 已撤销
	 */
	public static final int BET_ORDER_TYPE_CANCEL = 21004;
	
	/**
	 * 账户类型 现金
	 */
	public static final String ACCOUNT_TYPE_CASH = "cash";
	
	/**
	 * 账户类型 彩金
	 */
	public static final String ACCOUNT_TYPE_HANDSEL = "handsel";
	
	/**
	 * 分配配额
	 */
	public static final String DISTRIBUTION_QUOTA = "distribution";
	
	/**
	 * 回收配额
	 */
	public static final String RECYCLE_QUOTA = "recycle";
	
	/**
	 * 11选5彩种code（广东，上海，江西，山东，重庆，辽宁）
	 */
	public static final String SYXW_GD_LOTTERY_CODE="80001";
	public static final String SYXW_SH_LOTTERY_CODE="80002";
	public static final String SYXW_JX_LOTTERY_CODE="80003";
	public static final String SYXW_SD_LOTTERY_CODE="80004";
	public static final String SYXW_CQ_LOTTERY_CODE="80005";
	public static final String SYXW_LN_LOTTERY_CODE="80006";
	
	public static final String SSC_CQ_LOTTERY_CODE="70001";
	public static final String SSC_JX_LOTTERY_CODE="70002";
	
	public static final String FC3D_LOTTERY_CODE="50001";
	
	/**
	 * 投注类型,普通类型
	 */
	public static final String BET_TYPE="20000";
	
	/**
	 * 投注类型，追号类型
	 */
	public static final String BET_TYPE_TRACK="20001";
	
	/**
	 * 公告类型
	 */
	public static final String ARTICLE_TYPE_NOTICE="A2";
	
	/**
	 * 自动开奖
	 */
	public static final String AWARD_OPEN_TYPE_AUTO = "AUTO";
	
	/**
	 * 非号源中心自动开奖
	 */
	public static final String AWARD_OPEN_TYPE_PAUTO = "PAUTO";
	
	/**
	 * 手工录入号码开奖
	 */
	public static final String AWARD_OPEN_TYPE_HAND = "HAND";
	
	/**
	 * 手工自动开奖
	 */
	public static final String AWARD_OPEN_TYPE_HAUTO = "H_AUTO";
	
	/**
	 * 活动类型-注册
	 */
	public static final String ACTIVITY_TYPE_REG = "REG";
	
	/**
	 * 活动类型-首充
	 */
	public static final String ACTIVITY_TYPE_FRC = "FRC";
	/**
	 * 活动类型-单笔充值
	 */
	public static final String ACTIVITY_TYPE_ORC = "ORC";
	/**
	 * 活动类型-单笔充值
	 */
	public static final String ACTIVITY_LUCK_TYPE_ORC = "LORC";
	/**
	 * 活动类型-累充
	 */
	public static final String ACTIVITY_TYPE_TRC = "TRC";
	/**
	 * 活动类型-中奖
	 */
	public static final String ACTIVITY_TYPE_AWA = "AWA";
	/**
	 * 活动类型-投注
	 */
	public static final String ACTIVITY_TYPE_BET = "BET";
	/**
	 * 活动循环周期类型-当前区间
	 */
	public static final String CYCLE_TYPE_CUR = "CUR";
	/**
	 * 活动循环周期类型-上一区间
	 */
	public static final String CYCLE_TYPE_LAS = "LAS";
	/**
	 * 活动循环周期类型-递减区间
	 */
	public static final String CYCLE_TYPE_SUB = "SUB";
}
