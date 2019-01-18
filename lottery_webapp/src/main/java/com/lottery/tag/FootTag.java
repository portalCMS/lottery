package com.lottery.tag;


import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.xl.lottery.exception.LotteryExceptionLog;

public class FootTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4397590263658282356L;

	private String model = "<div class=\"paging mt20 tc lh22\">"
			+ "<a href=\"%1\">首页</a>"
			+ "<a href=\"%2\">上一页</a>"
			+ "%str"
			+ "<a href=\"%8\">下一页</a>"
			+ "<a href=\"%9\">尾页</a>&nbsp;&nbsp;共%sumpage页" + "</div>";

	private final String attr = "current";
	
	private final String url = "<a href=\"%url\" class=\"%att\">%y</a>";
	
	private final int pagecount = 5;
	
	private String action;
	private int page;
	private int sumpage;
	
	private boolean ajax=false;
	
	public boolean isAjax() {
		return ajax;
	}

	public void setAjax(boolean ajax) {
		this.ajax = ajax;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSumpage() {
		return sumpage;
	}

	public void setSumpage(int sumpage) {
		this.sumpage = sumpage;
	}

	public int doStartTag() {

		pageContext.getOut();// 获取JSP页面的输出流 out
		pageContext.getRequest();// 获取JSP页面的请求对象 request
		pageContext.getSession();// 获取JSP页面的会话对象 session
		pageContext.getServletContext();// 获取JSP页面的应用对象 application [Page]
		
		JspWriter out = pageContext.getOut();// 用pageContext获取out，他还能获取session等，基本上jsp的内置对象都能获取
		
		String div = "";
		if(ajax){
			div = "<div id='_myPageDiv'></div>"
				+ "<div class='paging mt20 tc lh22' style='display:none;'>"
				+ "<a href='' class='_p1'>首页</a>"
				+ "<a href='' class='_pb'>上一页</a>"
				+ "<span class='_pcs'></span>"
				+ "<a href='' class='_pn'>下一页</a>"
				+ "<a href=''  class='_pl'>尾页</a>"
				+ "&nbsp;&nbsp;共<span></span>页" 
				+ "</div>"
				+ "<div id='_mypc'><a href='' class='_pc' style='display:none;'></a></div>";
		}else{
			div = this.repaceModel(model, page, action, sumpage);
		}
		try {
			out.println(div);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LotteryExceptionLog.wirteLog(e);
		}

		return EVAL_PAGE;// 标签执行完后，继续执行后面的
	}

	public int doEndTag() {

		return SKIP_BODY;// 标签执行完后，不继续执行后面的
	}
	
	private String repaceModel(String model,int page,String action,int sumpage){
		model = model.replace("%1", action+"/"+1+".html");
		model = model.replace("%9", action+"/"+sumpage+".html");
		model = model.replace("%sumpage", Integer.toString(sumpage));
		if(page-1<=0){
			model = model.replace("%2", action+"/"+1+".html");
		}else{
			model = model.replace("%2", action+"/"+(page-1)+".html");
		}
		if(page+1>sumpage){
			model = model.replace("%8", action+"/"+sumpage+".html");
		}else{
			model = model.replace("%8", action+"/"+(page+1)+".html");
		}
		int tmeppage = page%this.pagecount;
		if(tmeppage == 0) tmeppage = 5;
		String str = "";
		if(page<=3){
			for(int i=0;i<5;i++){
				String stemp = "";
				if(i==sumpage)break;
				stemp =this.url;
				stemp =stemp.replace("%url", action+"/"+(i+1)+".html").replace("%y", Integer.toString(i+1));
				if(i+1==tmeppage)stemp =stemp.replace("%att", this.attr);
				str += stemp;
			}
		}else if(sumpage - page<3){
			for(int i=(sumpage-5);i<sumpage;i++){
				String stemp = "";
				if(i==sumpage)break;
				stemp =this.url;
				stemp =stemp.replace("%url", action+"/"+(i+1)+".html").replace("%y", Integer.toString(i+1));
				if(i+1==page)stemp =stemp.replace("%att", this.attr);
				str += stemp;
			}
		}else{
			String t1 = this.url;
			t1 =t1.replace("%url", action+"/"+(page-2)+".html").replace("%y", Integer.toString((page-2)));
			String t2 = this.url;
			t2 =t2.replace("%url", action+"/"+(page-1)+".html").replace("%y", Integer.toString((page-1)));
			String t3 = this.url;
			t3 =t3.replace("%url", action+"/"+page+".html").replace("%att", this.attr).replace("%y", Integer.toString(page));
			String t4 = this.url;
			t4 =t4.replace("%url", action+"/"+(page+1)+".html").replace("%y", Integer.toString(page+1));
			String t5 = this.url;
			t5 =t5.replace("%url", action+"/"+(page+2)+".html").replace("%y", Integer.toString(page+2));
			str = t1+t2+t3+t4+t5;
		}
		model = model.replace("%str", str);
		return model;
	}
	
	public static void main(String[] args) {
		FootTag t = new FootTag();
		t.model = t.model.replaceAll("c2", "1111");
		System.out.println(t.model);
	}
}
