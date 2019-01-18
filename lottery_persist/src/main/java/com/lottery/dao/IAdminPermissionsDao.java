package com.lottery.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.lottery.bean.entity.AdminPermissions;
import com.lottery.persist.generice.IGenericDao;


@Repository
public interface IAdminPermissionsDao extends IGenericDao<AdminPermissions>{

}
