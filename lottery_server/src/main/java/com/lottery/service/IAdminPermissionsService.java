package com.lottery.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lottery.bean.entity.AdminPermissions;

@Service
public interface IAdminPermissionsService{
	
	public void save() throws Exception;
	
	public void update() throws Exception;
}
