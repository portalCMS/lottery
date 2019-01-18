package com.xl.lottery.core.algorithm;

import java.util.Arrays;

import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.exception.LotteryExceptionLog;
public class Combination{

	private int[] index;//用于存储需要组合的数组的下标的成员变量。
	private int length;//表示待组合数组的长度。
	private int n;//组合序列的元素个数
	private long numLeft;//用于存储剩余组合序列个数的成员变量。
	private long total;//用于存储组合序列总数的成员变量。
	
	public Combination(int length){
		this(length,0);
	}
	
	public Combination(int length,int n) {		
		this.length=length;
		this.n=n;
		reset();
	}
	
	public void reset(){
		
		if(n>length){
			System.out.println("需要组合的个数超出数组元素个数！");
		}
		
		//初始化numLeft，开始时numLeft应该为2^n.
		total=numLeft=(int)Math.pow((double)2,(double)length);

		//初始化数组index。
		index=new int[length];
		Arrays.fill(index,0);
	}
	
	private int sum(){
		int s=0;
		for(int i:index){
			s+=i;
		}
		return s;
	}
	
	public boolean hasMore(){
		return numLeft > 0;
	}
	
	public int[] getNext(){

		index[0]+=1;
		for(int i=0;i<index.length;i++){
			if(index[i]==2){
				index[i]=0;
				if(i!=index.length-1) index[i+1]+=1;
			}
		}
		numLeft--;		
		if(this.n!=0){
			if(sum()==this.n) return index;
			else if(hasMore()) return getNext();
		}
		return index;
	}
}