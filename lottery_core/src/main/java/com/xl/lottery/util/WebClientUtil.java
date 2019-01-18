package com.xl.lottery.util;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;

public class WebClientUtil {
	public static WebClient getWebClient(){
		WebClient wc = new WebClient(BrowserVersion.CHROME);
		wc.getOptions().setCssEnabled(false);
		wc.getOptions().setThrowExceptionOnScriptError(false);
		wc.getOptions().setJavaScriptEnabled(false);
		wc.setJavaScriptTimeout(1500);
		return wc;
	}
}
