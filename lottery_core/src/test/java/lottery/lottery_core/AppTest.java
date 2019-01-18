package lottery.lottery_core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.sun.mail.handlers.text_html;


/**
1111111111111111
 * Unit test for simple App.
 */
public class AppTest {
	public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		Class clz = Class.forName("lottery.lottery_core.Test");
		Method[] methods = clz.getMethods();
		System.out.println(methods[0]);
		for(int i=0;i<methods.length;i++){
			if(methods[i].getName().equals("getTest")){
				System.out.println(methods[i]);
				boolean flag = true;
				Object[] aa = {"a",1};
				flag = (Boolean) methods[i].invoke(clz.newInstance(), aa);
				System.out.println(flag);
			}
		}
	}
}

class Test{
	
	public boolean getTest(String attr,int value){
		if(value==1){
			return true;
		}
		return false;
	}
}