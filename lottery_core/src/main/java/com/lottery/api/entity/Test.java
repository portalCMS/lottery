package com.lottery.api.entity;

import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.xl.lottery.GrabNo.ClientFactory;
import com.xl.lottery.desutil.DesUtil;
import com.xl.lottery.util.JsonUtil;
import com.xl.lottery.util.RandDomUtil;
import com.xl.lottery.util.WebClientUtil;

public class Test {

	public static void main(String[] args) throws Exception {
		//网易号源测试
		long b = System.currentTimeMillis();
		String links = "http://caipiao.163.com/award/jxssc/";
		WebClient wc = WebClientUtil.getWebClient();
		HtmlPage lotteryPage = wc.getPage(links);
		org.jsoup.nodes.Document doc = Jsoup.parse(lotteryPage.getWebResponse().getContentAsString());
		
		Elements es = doc.getElementsByAttribute("data-win-number");
		for(Element e : es){
			System.out.println("开奖号码："+e.attr("data-win-number").replace(" ", ",")+" "+e.attr("data-period"));
		}
		long c = System.currentTimeMillis();
		
		System.out.println(c-b);
	}

}
