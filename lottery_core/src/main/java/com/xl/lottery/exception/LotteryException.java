package com.xl.lottery.exception;

public class LotteryException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6639646740093964619L;

	public LotteryException(){
		
	}
	
	public LotteryException(String exception){
		super(exception);
	}
}
