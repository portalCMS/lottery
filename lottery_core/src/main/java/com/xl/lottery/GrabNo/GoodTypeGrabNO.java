package com.xl.lottery.GrabNo;

import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.lottery.api.entity.ReadData;
import com.lottery.api.entity.ReadMessage;
import com.lottery.api.entity.SendMessage;
import com.lottery.api.entity.StatusData;
import com.lottery.api.entity.StatusMessage;
import com.xl.lottery.desutil.DesUtil;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.util.CommonUtil;
import com.xl.lottery.util.DataDictionaryUtil;
import com.xl.lottery.util.DateUtil;
import com.xl.lottery.util.JsonUtil;
import com.xl.lottery.util.Util;
import com.xl.lottery.util.WebClientUtil;


public class GoodTypeGrabNO extends GrabNoAbstract{
	
	private RestTemplate rest = new RestTemplate();
	
	private String obtainData(String typeCode,String dateStr,String links){
		HttpEntity<String> html = rest.getForEntity(links, String.class);
		HttpHeaders headers = new HttpHeaders();
		List<String> cookies = html.getHeaders().get("Set-Cookie");
		for(String s:cookies){
			headers.add("Cookie", s);
		}
		headers.set("Accept","*/*");
		headers.set("Pragma","no-cache");
		headers.set("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:20.0) Gecko/20100101 Firefox/20.0");
		headers.set("X-Requested-With","XMLHttpRequest");
		headers.set("Cache-Control","no-cache");
		//headers.set("Accept-Encoding","gzip, deflate");
		headers.set("Accept-Language","zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		headers.set("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
		headers.set("Host", "data.shishicai.cn");
		headers.set("Referer", links);
		HttpEntity<String> en = new HttpEntity<String>("lottery="+typeCode+"&date="+dateStr, headers);
		HttpEntity<String> en2 = rest.postForEntity("http://data.shishicai.cn/handler/kuaikai/data.ashx",en, String.class);
		String data = en2.getBody();
		return data;
	}
	
	@Override
	public Map<String,List<LotteryVo>> invoke(final Map<String,Object> param) throws InterruptedException, LotteryException {
		List<String> urls =(List<String>) param.get("url");
		Map<String,Integer> linkLevelMap = (Map<String, Integer>) param.get("linkLevelMap");
		
		String lotTime = (String) param.get("lotTime");
		String typeCode = (String) param.get("typeCode");
		String issueNo = (String) param.get("issueNo");
		String lotteryType= (String)param.get("lotteryType");
		String lotteryGroup = (String) param.get("lotteryGroup");
		Boolean minusFlag= (Boolean) param.get("minusFlag");
		List<LotteryVo> list = new ArrayList<LotteryVo>();
		Map<String,List<LotteryVo>> returnMap = new HashMap<String, List<LotteryVo>>();
		SAXReader sax = new SAXReader();
		
		//循环配置的所有号源
		int level = 0;
		for(String links : urls){
			try{
				if(links.startsWith("http://data.shishicai.cn/")){
					String link = links.substring(0,links.indexOf("typeCode")-1)+"/";
					typeCode = links.substring(links.indexOf("typeCode")+9);
					String dateStr;
					if(minusFlag!=null&&minusFlag==true){
						dateStr =DateTime.now().minusDays(1).toString("yyyy-MM-dd");
					}else{
						dateStr =DateTime.now().toString("yyyy-MM-dd");
					}
					String data =obtainData(typeCode,dateStr,link);
					int first=-1;
					if(data!=null){//取回来可能为null
						first = data.indexOf("\""); 
					}
					if(first>-1){
						int second = data.indexOf("\"",first+1);
						String currentLot =data.substring(first+1, second);
						String[] arrs = currentLot.split(";");
						if(arrs.length==3){
							//重庆时时彩(typeCode=36)的奖期号不用去零
							String expectStr  = arrs[0];
							if(!typeCode.equals("4")){
								expectStr = arrs[0].replace("-0", "");
							}else{
								expectStr = arrs[0].replace("-", "");
							}
							
							String openCode = arrs[1];
							String openNum ="";
							if(openCode.indexOf(",")==-1){
								for(int i=0;i<openCode.length();i++){
									if(i!=openCode.length()-1){
										openNum += openCode.charAt(i)+",";
									}else{
										openNum += openCode.charAt(i);
									}
								}
							}else{
								openNum = openCode;
							}
							
							String openTime = arrs[2]+":00";
							//不用插入逗号
							String num = openNum;
							LotteryVo vo = new LotteryVo();
							vo.setExpectStr(expectStr);
							vo.setOpentime(openTime);
							vo.setNum(num);
							//检查开奖结果
							if(!this.checkOpenResult(issueNo, vo , list,links,linkLevelMap)){
								continue;
							}
							list.add(vo);
							//权重累加
							level += linkLevelMap.get(links);
						}
						
					}
				}else if(links.startsWith("http://www.cailele.com/")||links.startsWith("http://f.eboic.com/")
						||links.startsWith("http://www.eboic.com/")){
					URL url = new URL(links);
					URLConnection uc = url.openConnection();
					uc.setConnectTimeout(5000);
					Document document = sax.read(url);
					Element root = document.getRootElement();
					boolean isContinue = true;
					for(int i=0;i<root.elements().size();i++){
						Element eNode = (Element)root.elements().get(i);
						String expectStr = eNode.attribute("expect").getValue();
						String openTime = eNode.attribute("opentime").getValue();
						//抓取的期数
						String openCode = eNode.attribute("opencode").getValue();
						LotteryVo vo = new LotteryVo();
						vo.setExpectStr(expectStr);
						vo.setNum(openCode);
						vo.setOpentime(openTime);
						
						//检查开奖结果
						if(!this.checkOpenResult(issueNo, vo , list,links,linkLevelMap)){
							continue;
						}
						list.add(vo);
						
						isContinue = false;
						break;
					}
					//号源网址无法获取开奖结果则抓其它号源
					if(isContinue){
						continue;
					}
					//权重累加
					level += linkLevelMap.get(links);
				}else if(links.startsWith("http://www.9188.com/")){
					String links2="";
					//抓取的期数
					String pid ="pid";
					String atime ="atime";
					String acode ="acode";
					//福彩3D和排三特殊处理
					if(links.indexOf("l10.xml")!=-1){
						pid ="cp";
						atime="tm";
						acode="cc";
						links2 = links;
					}else{
						links2 = links+DateUtil.getStringDateShort()+".xml";
					}
					
					URL url = new URL(links2);
					Document document = sax.read(url);
					Element root = document.getRootElement();
					boolean isContinue = true;
					for(int i=0;i<root.elements().size();i++){
						Element eNode = (Element)root.elements().get(i);
						String expectStr = eNode.attribute(pid).getValue();
						String openTime = eNode.attribute(atime).getValue();
						String openCode = eNode.attribute(acode).getValue();
						LotteryVo vo = new LotteryVo();
						vo.setExpectStr(expectStr);
						vo.setNum(openCode);
						vo.setOpentime(openTime);
						
						//检查开奖结果
						if(!this.checkOpenResult(issueNo, vo , list,links,linkLevelMap)){
							continue;
						}
						list.add(vo);
						
						isContinue = false;
						break;
					}
					//号源网址无法获取开奖结果则抓其它号源
					if(isContinue){
						continue;
					}
					//权重累加
					level += linkLevelMap.get(links);
				}else if(links.startsWith("http://cp.360.cn/")){
					WebClient wc = WebClientUtil.getWebClient();
					HtmlPage lotteryPage = wc.getPage(links);
					org.jsoup.nodes.Document doc = Jsoup.parse(lotteryPage.getWebResponse().getContentAsString());
					
					String expectStr="";
					String openCode="";
					String openTime = DateUtil.getStringDateShort()+" "+lotTime;
					//福彩3d或者排列3号源
					if(links.indexOf("/sd/")!=-1||links.indexOf("/p3/")!=-1){
						String infoClass="";
						if(links.indexOf("/sd/")!=-1){
							infoClass = "kpkjcode";
						}else{
							infoClass = "panel-test";
						}
						Elements es1 = doc.getElementsByClass(infoClass);
						Elements es2 = es1.get(0).getElementsByTag("tr").get(1).getElementsByTag("td");
						expectStr =es2.get(0).text();
						openCode = es2.get(1).text();
						String openNum="";
						for(int i =0;i<openCode.trim().length();i++){
							if(i==openCode.trim().length()-1){
								openNum += openCode.substring(i,i+1);
							}else{
								openNum += openCode.substring(i,i+1)+",";
							}
						}
						openCode = openNum;
					}else{
						Elements es1 = doc.getElementById("open_issue").getAllElements();
						expectStr = DateUtil.getCurrYear()+es1.get(0).text();
						
						Elements es = doc.getElementById("open_code_list").getAllElements();
						openCode = es.get(0).text().replaceAll(" ", ",");
						if(openCode.equals("?,?,?,?,?")){
							continue;
						}
					}
					
					LotteryVo vo = new LotteryVo();
					vo.setExpectStr(expectStr);
					vo.setNum(openCode);
					vo.setOpentime(openTime);
					//检查开奖结果
					if(!this.checkOpenResult(issueNo, vo , list,links,linkLevelMap)){
						continue;
					}
					list.add(vo);
					//权重累加
					level += linkLevelMap.get(links);
				}else if(links.startsWith("http://www.xjflcp.com/ssc/")){
					WebClient wc = WebClientUtil.getWebClient();
					HtmlPage lotteryPage = wc.getPage(links);
					org.jsoup.nodes.Document doc = Jsoup.parse(lotteryPage.getWebResponse().getContentAsString());
					org.jsoup.nodes.Element awardArea = doc.getElementById("showLottryDrawTable");
					boolean isContinue = true;
					for(int i=1;i<11;i++){
						Elements es2 =  awardArea.getElementsByTag("tr").get(i).getElementsByTag("td");
						LotteryVo vo = new LotteryVo();
						vo.setExpectStr(es2.get(0).text().substring(2));
						vo.setOpentime(DateUtil.getStringDate());
						vo.setNum(es2.get(2).text().replaceAll(" ", ","));
						//检查开奖结果
						if(!this.checkOpenResult(issueNo, vo , list,links,linkLevelMap)){
							continue;
						}
						list.add(vo);
						
						isContinue = false;
						break;
					}
					//号源网址无法获取开奖结果则抓其它号源
					if(isContinue){
						continue;
					}
					//权重累加
					level += linkLevelMap.get(links);
				}else if(links.startsWith("http://www.kaihaoma.com/center")){
					String oriLinks = links;
					oriLinks = oriLinks.trim();
					//自主彩种特殊处理
					String dateKey = "2_"+System.currentTimeMillis()/1000;
					String entryInfo = DesUtil.encrypt(dateKey, "cwx01409");
					String lotteryid = oriLinks.substring(oriLinks.indexOf("?id=")+4,oriLinks.length());
					oriLinks = oriLinks.substring(0,oriLinks.indexOf("?id="));
					String numLinks =oriLinks+"/numbercenter/getNumber";
							
					ReadMessage msg = new ReadMessage();
					msg.setKey(entryInfo);
					msg.setPlatid("2");
					
					ReadData data = new ReadData();
					data.setIssue(issueNo);
					data.setLotteryid(lotteryid);
					
					msg.setData(data);
					
					String params = JsonUtil.objToJson(msg);
					
					String jsonStr = ClientFactory.getUrl(numLinks,"msg="+params);
					SendMessage result=null;
					if(!jsonStr.startsWith("e")){
						result =JsonUtil.jsonToObject(jsonStr, SendMessage.class) ;
					}
					
					if(null==result||!StringUtils.isEmpty(result.getErrorCode())){
						throw new LotteryException("彩种["+lotteryType+"],第"+issueNo+"期,自主号源抓号异常,异常代码："+jsonStr);
					}else{
						LotteryVo vo = new LotteryVo();
						vo.setExpectStr(issueNo);
						vo.setNum(result.getData().getCode().replaceAll(" ", ","));
						vo.setOpentime(DateUtil.getStringDate());
						//检查开奖结果
						if(!this.checkOpenResult(issueNo, vo , list,links,linkLevelMap)){
							continue;
						}
						list.add(vo);
					}
					
					//给号源中心发送号源抓取成功的信息
					dateKey = "2_"+System.currentTimeMillis()/1000;
					entryInfo = DesUtil.encrypt(dateKey, "cwx01409");
					StatusMessage smsg = new StatusMessage();
					smsg.setKey(entryInfo);
					smsg.setPlatid("2");
					
					StatusData sdata = new StatusData();
					sdata.setIssue(issueNo);
					sdata.setLotteryid(lotteryid);
					sdata.setStatus("1");
					
					smsg.setData(sdata);
					params = JsonUtil.objToJson(smsg);
					
					String cfLinks =oriLinks+"/numbercenter/results";
					ClientFactory.getResultUrl(cfLinks,"msg="+params);
					//权重累加
					level += linkLevelMap.get(links);
				}else if(links.startsWith("http://result.168kai.com/List.aspx")){
					String params = links.substring(links.indexOf("codes"));
					String str = ClientFactory.getResultUrl("http://result.168kai.com/List.aspx",params);
					int end = str.indexOf("nTerm");
					int start = str.indexOf("cTermDT");
					String lotInfo =str.substring(start-1, end-3);
					String openTime =lotInfo.split("\",\"")[0].substring(11).replaceAll("/", "-");
					String expectStr = lotInfo.split("\",\"")[1].substring(8);
					String openCode = lotInfo.split("\",\"")[2].substring(14);
					String[] openNums = openCode.trim().split(",");
					StringBuffer sb = new StringBuffer("");
					for(int j=0;j<openNums.length;j++){
						//如果是11选五开奖号码小于10的需要加0.
						int num = Integer.parseInt(openNums[j]);
						String numStr ="";
						if(lotteryGroup.equals(CommonUtil.LOTTERY_GROUP_SYXW)&&num<10){
							numStr = "0"+num;
						}else{
							numStr=""+num;
						}
						
						if(j==0){
							sb.append(numStr);
						}else{
							sb.append(",").append(numStr);
						}
					}
					LotteryVo vo = new LotteryVo();
					vo.setExpectStr(expectStr);
					vo.setNum(sb.toString());
					vo.setOpentime(openTime);
					//检查开奖结果
					if(!this.checkOpenResult(issueNo, vo , list,links,linkLevelMap)){
						continue;
					}
					list.add(vo);
					//权重累加
					level += linkLevelMap.get(links);
				}else if(links.startsWith("http://www.huacai.com/")){
					//华彩网天津时时彩
					WebClient wc = WebClientUtil.getWebClient();
					HtmlPage lotteryPage = wc.getPage(links);
					org.jsoup.nodes.Document doc = Jsoup.parse(lotteryPage.getWebResponse().getContentAsString());
					Elements es1 = doc.getElementsByClass("list2_table");
					Elements es2 = es1.get(0).getElementsByTag("tr").get(1).getElementsByTag("td");
					String expectStr = es2.get(0).ownText();
//					String openTime = es2.get(1).ownText();
					String openTime = DateUtil.getStringDateShort()+" "+lotTime;
					String openCode = es2.get(2).text().replaceAll(" ", ",");
					
					LotteryVo vo = new LotteryVo();
					vo.setExpectStr(expectStr);
					vo.setNum(openCode);
					vo.setOpentime(openTime);
					//检查开奖结果
					if(!this.checkOpenResult(issueNo, vo , list,links,linkLevelMap)){
						continue;
					}
					list.add(vo);
					//权重累加
					level += linkLevelMap.get(links);
				}
				
				//号源及对应的抓号结果放入map中
				returnMap.put(links, list);
				//如果权重累加大于等于100，则终止循环
				if(level>=100){
					break;
				}
				
			}catch(Exception e){
				//异常不用理会，继续拿其它号源数据
				e.printStackTrace();
				continue;
			}
		}
		
		return returnMap;
	}
	
	/**
	 * 检查开奖结果
	 * @param taskIssueNo
	 * @param lotvo
	 * @return
	 * @throws ParseException 
	 */
	private boolean checkOpenResult(String taskIssueNo , LotteryVo lotvo,
			List<LotteryVo> openedVos,String links,Map<String,Integer> linkLevelMap ) throws ParseException{
		String series =lotvo.getExpectStr();
		//山东11选5不用截取前两位,自主彩种奖期号也不用截取
		if((series.length()>8 && series.substring(0,8).equals(DateUtil.getStrDateShort()))
				||(series.length()>4 && series.substring(0,4).equals(DateUtil.getCurrYear()))){
			series = series.substring(2);
		}
		//如果开奖号码不是整数则返回false;
		if(!Util.isInteger(lotvo.getNum().split(",")[0])){
			return false;
		}
		//必须获取到当前期的开奖结果
		if(series.equals(taskIssueNo)){
			//高权重的号源，不用和低权重的号源比对开奖结果，直接返回高权重的开奖结果即可。
			if(linkLevelMap.get(links)>=100){
				openedVos.clear();
				return true;
			}
			//低权重的号源，必须比对开奖结果
			for(LotteryVo t : openedVos){
				if(!t.getNum().equals(lotvo.getNum())){
					return false;
				}
			}
			
			return true;
		}
		
		return false;
	}

}
