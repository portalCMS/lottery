package test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * The persistent class for the game_time database table.
 * 彩票期号表
 */
@Entity
public class GameTime  {
	private static final long serialVersionUID = 1L;


	//开奖时间
	@Column(name="lot_time")
	private String lotTime;
	//彩票类型
	@Column(name="lottery_type")
	private String lotteryType;
	//奖期号
	private String serial;
	//投注开始时间
	private String startBetTime;
	//投注结束时间
	private String endBetTime;
	//抓取次数
	private Integer catchTimes;
	
	public GameTime() {
	}


	public String getLotTime() {
		return this.lotTime;
	}

	public void setLotTime(String lotTime) {
		this.lotTime = lotTime;
	}

	public String getLotteryType() {
		return this.lotteryType;
	}

	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}


	public String getSerial() {
		return serial;
	}


	public void setSerial(String serial) {
		this.serial = serial;
	}


	public String getStartBetTime() {
		return startBetTime;
	}


	public void setStartBetTime(String startBetTime) {
		this.startBetTime = startBetTime;
	}


	public String getEndBetTime() {
		return endBetTime;
	}


	public void setEndBetTime(String endBetTime) {
		this.endBetTime = endBetTime;
	}


	public Integer getCatchTimes() {
		return catchTimes;
	}


	public void setCatchTimes(Integer catchTimes) {
		this.catchTimes = catchTimes;
	}
	
}