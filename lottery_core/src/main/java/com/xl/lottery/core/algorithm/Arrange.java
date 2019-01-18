package com.xl.lottery.core.algorithm;

import java.math.*;
import java.util.*;

public class Arrange{
	private int[] index;//用于存储需要全排列的数组的下标的成员变量。
	private int n;//表示待排数组的元素个数。
	private long numLeft;//用于存储剩余排列序列个数的成员变量。
	private long total;//用于存储排列序列总数的成员变量。
	
	public Arrange(int n){
		this.n=n;
		reset();//调用重置
	}
	
	public void reset(){
		//初始化数组index。
		index=new int[n];
		for(int i = 0; i < index.length; i++){
			index[i] = i;
		}
		
		//初始化numLeft，开始时numLeft应该为n!.
		total=1;
		for(int i = n;i>1;i--){
			total*=i;
		}
		numLeft=total;
	}

	//判断是否排序结束
	public boolean hasMore(){
		return numLeft > 0;
	}


	//得到下一个排列序列
	public int[] getNext(){

		if (numLeft==total){
			numLeft -=1;
			return index;
		}

		int j = index.length - 2;
		while (index[j] > index[j + 1]){
			j--;
		}

		int k = index.length - 1;
		while (index[j] > index[k]){
			k--;
		}
		
		int temp;
		temp = index[k];
		index[k] = index[j];
		index[j] = temp; 

		int r = index.length - 1;
		int s = j + 1;

		while (r > s){
			temp = index[s];
			index[s] = index[r];
			index[r] = temp;
			r--;
			s++;
		}

		numLeft-=1;
		return index;

	}


}