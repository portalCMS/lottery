package com.lottery.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.LotteryPlaySelect;

@Service
public interface ILotteryPlaySelectService {

	public List<LotteryPlaySelect> getAllSelectCode()throws Exception;
}
