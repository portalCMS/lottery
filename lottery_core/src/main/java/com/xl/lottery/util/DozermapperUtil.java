package com.xl.lottery.util;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

/**
 * 新的对象拷贝
 * @author CW-HP7
 *
 */
public class DozermapperUtil {

	private static Mapper instance;
	
	private DozermapperUtil() {  
	        //shoudn't invoke.  
	} 
	
	public static synchronized Mapper getInstance() {  
        if (instance == null) {  
            instance = new DozerBeanMapper();  
        }  
        return instance;  
    }  
}
