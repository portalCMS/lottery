package com.xl.lottery.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

	private static ObjectMapper mapper;
	
	public static ObjectMapper getMapper() {
		if(mapper == null) mapper = new ObjectMapper();
		return mapper;
	}
	
	public static String objToJson(Object obj) {
		String jsonstr;
		try {
			jsonstr = getMapper().writeValueAsString(obj);
			return jsonstr;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> T jsonToObject(String jsonString, Class<T> pojoCalss){
		T t;
		try {
			t = getMapper().readValue(jsonString,pojoCalss);
			return t;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<?> jsonToObjectList(String jsonString){
		List<?> list=null;
		try {
			list = getMapper().readValue(jsonString,List.class);
			return list;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		 String json = "[{\"address\": \"address2\",\"name\":\"haha2\",\"id\":2,\"email\":\"email2\"},"+
	                "{\"address\":\"address\",\"name\":\"haha\",\"id\":1,\"email\":\"email\"}]";
		 
	    try {
	        List<LinkedHashMap<String, Object>> list = getMapper().readValue(json, List.class);
	        System.out.println(list.size());
	        for (int i = 0; i < list.size(); i++) {
	            Map<String, Object> map = list.get(i);
	            Set<String> set = map.keySet();
	            for (Iterator<String> it = set.iterator();it.hasNext();) {
	                String key = it.next();
	                System.out.println(key + ":" + map.get(key));
	            }
	        }
	    } catch (JsonParseException e) {
	        e.printStackTrace();
	    } catch (JsonMappingException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}


