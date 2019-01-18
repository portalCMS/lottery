package com.lottery.dao.impl;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.Query;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;

import com.lottery.dao.IOrderSequenceDao;
import com.lottery.persist.generice.GenericDAO;
import com.xl.lottery.exception.LotteryException;
import com.xl.lottery.exception.LotteryExceptionLog;

@Repository
public class OrderSequenceDaoImpl extends GenericDAO implements IOrderSequenceDao {

	public OrderSequenceDaoImpl() {
		super(Object.class);
	}

	@Override
	public String getOrderSequence(final String orderType,final int num) {
//		Query query = getSession().createSQLQuery("{Call generate_orderNo(?,?)}");
//		query.setParameter(0, orderType);
//		query.setParameter(1, num);
		String orderNum = "";
		orderNum = getSession().doReturningWork(new ReturningWork<String>() {
			@Override
			public String execute(Connection connection) {
				// TODO Auto-generated method stub
				 CallableStatement cs = null;
				 String str="";
				try {
					cs = connection.prepareCall("{ call generate_orderNo(?,?,?)}");
					 cs.setString(1, orderType);
					 cs.setInt(2, num);
					 cs.registerOutParameter(3, Types.VARCHAR);
					 cs.executeQuery();
					 str = cs.getString(3);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					releaseQuietly(cs);
				}
				return str;
				
			}
		});
		return orderNum;
	}

	private void releaseQuietly(CallableStatement cs) {  
        if(cs==null){
        	return;
        }
        try {
			cs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LotteryExceptionLog.wirteLog(e);
		}
    }  
}
