package com.xl.lottery.exception;

import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class LotteryExceptionLog {

	private static Logger logger = LoggerFactory
			.getLogger(LotteryExceptionLog.class);

	public static void wirteLog(Exception e) {
		e.printStackTrace();
		long errorNo = System.currentTimeMillis();
		logger.error("错误编号{" + errorNo + "}:" + e.getMessage(),e);

	}
	public static void wirteLog(Exception e,String extendMessage) {
		e.printStackTrace();
		long errorNo = System.currentTimeMillis();
		logger.error("错误编号{" + errorNo + "}:" + e.getMessage()+"("+extendMessage+")",e);

	}
	public static void wirteLog(Exception e, Map<String, Object> model) {
		try {
			if (e instanceof LotteryException) {
				e.printStackTrace();
				long errorNo = System.currentTimeMillis();
				model.put("errorMsg",e.getMessage());
				logger.error("错误编号{" + errorNo + "}:" + e.getMessage(),e);
			} else {
				e.printStackTrace();
				long errorNo = System.currentTimeMillis();
				model.put("errorMsg","运行时异常 错误编号 "+errorNo);
				logger.error("错误编号{" + errorNo + "}:" + e.getMessage(),e);
			}
		} catch (Exception e2) {
			// TODO: handle exception
			e2.printStackTrace();
			logger.error(e2.getMessage(),e2);
		}
	}

	@SuppressWarnings("null")
	public static void wirteLog(Exception e, Map<String, Object> model,
			String errorMsg) {
		try {
			if (e instanceof LotteryException) {
				if (errorMsg != null || !(errorMsg.equals(""))) {
					model.put("errorMsg", errorMsg);
					long errorNo = System.currentTimeMillis();
					logger.error("错误编号{" + errorNo + "}:" + e.getMessage(),e);
				} else {
					model.put("errorMsg",e.getMessage());
					long errorNo = System.currentTimeMillis();
					logger.error("错误编号{" + errorNo + "}:" + e.getMessage(),e);
				}
			} else {
				e.printStackTrace();
				if (errorMsg != null || !(errorMsg.equals(""))) {
					long errorNo = System.currentTimeMillis();
					model.put(
							"errorMsg","运行时异常 错误编号 "+errorNo);
					logger.error("错误编号{" + errorNo + "}:" + e.getMessage(),e);
				} else {
					long errorNo = System.currentTimeMillis();
					model.put("errorMsg", "运行时异常 错误编号:" + errorNo);
					logger.error("错误编号{" + errorNo + "}:" + e.getMessage(),e);
				}
			}
		} catch (Exception e2) {
			// TODO: handle exception
			e2.printStackTrace();
			logger.error(e2.getMessage());
		}
	}

	public static void wirteLog(Exception e, HttpServletRequest request) {
		try {
			if (e instanceof LotteryException) {
				request.setAttribute("errorMsg",e.getMessage());
				long errorNo = System.currentTimeMillis();
				logger.error("错误编号{" + errorNo + "}:" + e.getMessage(),e);
			} else {
				e.printStackTrace();
				long errorNo = System.currentTimeMillis();
				request.setAttribute("errorMsg","运行时异常 错误编号 "+errorNo);
				logger.error("错误编号{" + errorNo + "}:" + e.getMessage(),e);
			}
		} catch (Exception e2) {
			// TODO: handle exception
			e2.printStackTrace();
			logger.error(e2.getMessage());
		}
	}

	@SuppressWarnings("null")
	public static void wirteLog(Exception e, HttpServletRequest request,
			String errorMsg) {
		try {
			if (e instanceof LotteryException) {
				if (errorMsg != null || !(errorMsg.equals(""))) {
					request.setAttribute("errorMsg",errorMsg);
					long errorNo = System.currentTimeMillis();
					logger.error("错误编号{" + errorNo + "}:" + errorMsg,errorMsg);
				} else {
					request.setAttribute("errorMsg",e.getMessage());
					long errorNo = System.currentTimeMillis();
					logger.error("错误编号{" + errorNo + "}:" + e.getMessage(),e);
				}
			} else {
				e.printStackTrace();
				if (errorMsg != null || !(errorMsg.equals(""))) {
					long errorNo = System.currentTimeMillis();
					request.setAttribute("errorMsg","运行时异常 错误编号 "+errorNo);
					logger.error("错误编号{" + errorNo + "}:" + e.getMessage(),e);
				} else {
					long errorNo = System.currentTimeMillis();
					request.setAttribute("errorMsg","运行时异常 错误编号 :" +errorMsg);
					logger.error("错误编号{" + errorNo + "}:" + e.getMessage(),e);
				}
			}
		} catch (Exception e2) {
			// TODO: handle exception
			e2.printStackTrace();
			logger.error(e2.getMessage(),e2);
		}
	}
	
	
	public static void wirteLog(Exception e, RedirectAttributes redirectAttributes) {
		try {
			if (e instanceof LotteryException) {
				e.printStackTrace();
				long errorNo = System.currentTimeMillis();
				redirectAttributes.addFlashAttribute("errorMsg",e.getMessage());
				logger.error("错误编号{" + errorNo + "}:" + e.getMessage(),e);
			} else {
				e.printStackTrace();
				long errorNo = System.currentTimeMillis();
				redirectAttributes.addFlashAttribute("errorMsg","运行时异常 错误编号 "+errorNo);
				logger.error("错误编号{" + errorNo + "}:" + e.getMessage(),e);
			}
		} catch (Exception e2) {
			// TODO: handle exception
			e2.printStackTrace();
			logger.error(e2.getMessage(),e2);
		}
	}

	@SuppressWarnings("null")
	public static void wirteLog(Exception e,RedirectAttributes redirectAttributes,
			String errorMsg) {
		try {
			if (e instanceof LotteryException) {
				if (errorMsg != null || !(errorMsg.equals(""))) {
					redirectAttributes.addFlashAttribute("errorMsg", errorMsg);
					long errorNo = System.currentTimeMillis();
					logger.error("错误编号{" + errorNo + "}:" + errorMsg,errorMsg);
				} else {
					redirectAttributes.addFlashAttribute("errorMsg",e.getMessage());
					long errorNo = System.currentTimeMillis();
					logger.error("错误编号{" + errorNo + "}:" + e.getMessage(),e);
				}
			} else {
				e.printStackTrace();
				if (errorMsg != null || !(errorMsg.equals(""))) {
					long errorNo = System.currentTimeMillis();
					redirectAttributes.addFlashAttribute(
							"errorMsg","运行时异常 错误编号 "+errorNo);
					logger.error("错误编号{" + errorNo + "}:" + e.getMessage(),e);
				} else {
					long errorNo = System.currentTimeMillis();
					redirectAttributes.addFlashAttribute("errorMsg","运行时异常 错误编号:" + errorMsg);
					logger.error("错误编号{" + errorNo + "}:" + e.getMessage(),e);
				}
			}
		} catch (Exception e2) {
			// TODO: handle exception
			e2.printStackTrace();
			logger.error(e2.getMessage(),e2);
		}
	}
}
