/**
 * 
 */
package com.xl.lottery.util;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author xyxcxzl
 *
 */
public class ObjectMapperForDate extends ObjectMapper{
	private static final long serialVersionUID = 1L;

	public ObjectMapperForDate(){
		//this.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
		this.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		//this.configure(SerializationConfig..WRITE_NULL_MAP_VALUES, false);
		//this.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);	
		//this.setDateFormat(df);
	}
}
