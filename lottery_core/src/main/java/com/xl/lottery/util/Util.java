/*
* _08LumaQQ - Java _08QQ Client
*
* Copyright (C) 2004 luma <stubma@163.com>
*                    notXX
*
* This program is free software; you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation; either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/
package com.xl.lottery.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 工具类，提供一些方便的方法，有些主要是用于调试用途，有些不是
 *
 * @author luma
 * @author notXX
 */
public class Util {
    // Log
    private static Logger logger = LoggerFactory.getLogger(Util.class);
    
    /**
     * 根据某种编码方式将字节数组转换成字符串
     *
     * @param b        字节数组
     * @param offset   要转换的起始位置
     * @param len      要转换的长度
     * @param encoding 编码方式
     * @return 如果encoding不支持，返回一个缺省编码的字符串
     */
    public static String getString(byte[] b, int offset, int len, String encoding) {
        try {
            return new String(b, offset, len, encoding);
        } catch (UnsupportedEncodingException e) {
            return new String(b, offset, len);
        }
    }
    
    /**
     * @param ip ip的字节数组形式
     * @return 字符串形式的ip
     */
    public static String getIpStringFromBytes(byte[] ip) {
    	StringBuilder sb = new StringBuilder();
        sb.delete(0, sb.length());
        sb.append(ip[0] & 0xFF);
        sb.append('.');
        sb.append(ip[1] & 0xFF);
        sb.append('.');
        sb.append(ip[2] & 0xFF);
        sb.append('.');
        sb.append(ip[3] & 0xFF);
        return sb.toString();
    }

    /**
     * 从ip的字符串形式得到字节数组形式
     *
     * @param ip 字符串形式的ip
     * @return 字节数组形式的ip
     */
    public static byte[] getIpByteArrayFromString(String ip) {
        byte[] ret = new byte[4];
        StringTokenizer st = new StringTokenizer(ip, ".");
        try {
            ret[0] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
            ret[1] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
            ret[2] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
            ret[3] = (byte) (Integer.parseInt(st.nextToken()) & 0xFF);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ret;
    }
    
  /**
   * 阿拉伯数字转中文小写 (暂时只支持到万级别)
   * @param si
   */
  	 public static String transition(Object si){
	  	  String []aa={"","十","百","千","万","十万","百万","千万","亿","十亿"};
	  	  String []bb={"一","二","三","四","五","六","七","八","九"};
	  	  char[] ch=String.valueOf(si).toCharArray();
	  	  int maxindex=ch.length;
	  	  StringBuffer returnStr=new StringBuffer();
	  	  // 字符的转换
	  	  //两位数的特殊转换
	  	  if(maxindex==2){
	  		  for(int i=maxindex-1,j=0;i>=0;i--,j++){
	  			  	if(ch[j]!=48){
	  			  		if(j==0&&ch[j]==49){
	  			  			returnStr = returnStr.append(aa[i]);
	  			  		}else{
	  			  			returnStr = returnStr.append(bb[ch[j]-49]+aa[i]);
	  			  		}
	  			  	}
	  		  }
	  	   //其他位数的特殊转换，使用的是int类型最大的位数为十亿
	  	  }else{
		  	   for(int i=maxindex-1,j=0;i>=0;i--,j++){
			  	    if(ch[j]!=48){
			  	    	returnStr = returnStr.append(bb[ch[j]-49]+aa[i]);
			  	    }
		  	   }
	  	  }
	  	  
		return returnStr.toString();
  	 } 
    
  	 /**
  	  * 清零
  	  * @param str 原始字符串
  	  * @return
  	  */
  	 public static String trimZero(String str) {
  	  if (str.indexOf(".") != -1 && str.charAt(str.length() - 1) == '0') {
  	   return trimZero(str.substring(0, str.length() - 1));
  	  } else {
  	   return str.charAt(str.length() - 1) == '.' ? str.substring(0, str.length() - 1) : str;
  	  }
  	 }
  	 /**
  	  * 读文件
  	  * @param path
  	  * @return
  	  * @throws IOException
  	  */
  	 public static String BufferedReaderFile(String path) throws IOException{
         File file=new File(path);
         if(!file.exists()||file.isDirectory())
             throw new FileNotFoundException();
         InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "GBK");
         BufferedReader br=new BufferedReader(isr);
         String temp=null;
         StringBuffer sb=new StringBuffer();
         temp=br.readLine();
         while(temp!=null){
             sb.append(temp+" ");
             temp=br.readLine();
         }
         //关闭流，不可少
	     if (br != null){
	    	 br.close();
	     }
         return sb.toString();
     }
  	 
  	 /**
  	  * 正则表达式将空格，换行符，制表符，等替换为逗号
  	  * @param str
  	  * @return
  	  */
  	public static String getStringNoBlank(String str) {      
        if(str!=null && !"".equals(str)) {      
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");      
            Matcher m = p.matcher(str);      
            String strNoBlank = m.replaceAll("");      
            return strNoBlank;      
        }else {      
            return str;      
        }           
    } 
  	/**
  	 * 检查字符串是否是整数
  	 * @param input
  	 * @return
  	 */
  	 public static boolean isInteger(String input){  
         Matcher mer = Pattern.compile("^[0-9]+$").matcher(input);  
         return mer.find();  
     }  
  	
  	 public static void main(String[] args){
  		 System.out.println(isInteger("01"));
  	 }
}
