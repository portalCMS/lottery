package com.xl.lottery.GrabNo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.xl.lottery.util.GetProperty;

/**
 * 
 * @ClassName: ClientFactory 
 * @Description: TODO(请求接口) 
 * @author yong_jiang 
 * @date 2014-5-19 上午11:48:10 
 *
 */
public class ClientFactory {
	protected final static Log log = LogFactory.getLog(ClientFactory.class);

	public final static String PROFILE_CXF = "client-profile.properties";
	/* 提交方式*/
	private static String SEND_TYPE= null;
	/* 提交数据类型*/
	private static String ACCEPT_TYPE= null;
	/*连接超时时间*/
	private static int TIMEOUT_CONN;
	/*等待超时时间*/
	private static int TIMEOUT_RECV;
	 /* 载入配置 */
    static {
    	try {
    		SEND_TYPE = GetProperty.getPropertyByName2(PROFILE_CXF, "SEND_TYPE");
    		ACCEPT_TYPE = GetProperty.getPropertyByName2(PROFILE_CXF, "ACCEPT_TYPE");
    		TIMEOUT_CONN = Integer.parseInt(GetProperty.getPropertyByName2(PROFILE_CXF, "TIMEOUT_CONN"));
    		TIMEOUT_RECV= Integer.parseInt(GetProperty.getPropertyByName2(PROFILE_CXF, "TIMEOUT_RECV"));
    	}
		catch (Exception e) {
			log.error(e);
		}
    }
	
	  public static String getUrl(String myurl,String params)throws  Exception{
		  String url= myurl+"?"+params;
          String lines=""; 
          String str = "";
          URL getUrl = new URL(url); 
          // 根据拼凑的URL，打开连接，URL.openConnection()函数会根据 URL的类型，返回不同的URLConnection子类的对象，在这里我们的URL是一个http，因此它实际上返回的是HttpURLConnection 
          HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection(); 
          connection.setConnectTimeout(TIMEOUT_CONN); //连接超时
          connection.setReadTimeout(TIMEOUT_RECV);//等待超时
          // 建立与服务器的连接，并未发送数据 
          connection.connect(); 
          // 发送数据到服务器并使用Reader读取返回的数据 
          BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())); 
          while ((lines = reader.readLine()) != null) { 
       	       str += lines;
          } 
          reader.close(); 
          // 断开连接 
          connection.disconnect(); 
          return str;
	  }
	  
	  public static String getUserUrl(String myurl,String params)throws  Exception{
		  String url= myurl;
          String lines=""; 
          String str = "";
          URL getUrl = new URL(url); 
          // 根据拼凑的URL，打开连接，URL.openConnection()函数会根据 URL的类型，返回不同的URLConnection子类的对象，在这里我们的URL是一个http，因此它实际上返回的是HttpURLConnection 
          HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
          connection.setDoOutput(true);//是否传入参数
          connection.setDoInput(true);//是否返回参数
          connection.setRequestMethod(SEND_TYPE); //post提交
          connection.setUseCaches(false); //
          connection.setInstanceFollowRedirects(true);//是否连接遵循重定向
          connection.setRequestProperty("Content-Type", ACCEPT_TYPE);//提交方式
          connection.setConnectTimeout(TIMEOUT_CONN); //连接超时
          connection.setReadTimeout(TIMEOUT_RECV);//等待超时
          // 建立与服务器的连接，并未发送数据 
          byte[] bypes = params.toString().getBytes();
          connection.getOutputStream().write(bypes);
          // 发送数据到服务器并使用Reader读取返回的数据 
          BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())); 
          while ((lines = reader.readLine()) != null) { 
       	       str += lines;
          } 
          reader.close(); 
          // 断开连接 
          connection.disconnect(); 
          return str;
	  }

	  public static String getResultUrl(String myurl,String params)throws  Exception{
		  String url= myurl+"?"+params;
		  String lines=""; 
          String str = "";
          URL getUrl = new URL(url); 
          // 根据拼凑的URL，打开连接，URL.openConnection()函数会根据 URL的类型，返回不同的URLConnection子类的对象，在这里我们的URL是一个http，因此它实际上返回的是HttpURLConnection 
          HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection(); 
          connection.setConnectTimeout(TIMEOUT_CONN); //连接超时
          connection.setReadTimeout(TIMEOUT_RECV);//等待超时
          // 建立与服务器的连接，并未发送数据 
          connection.connect(); 
          // 发送数据到服务器并使用Reader读取返回的数据 
          BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())); 
          while ((lines = reader.readLine()) != null) { 
       	       str += lines;
          } 
          reader.close(); 
          // 断开连接 
          connection.disconnect(); 
          return str;
	  }
}
