package com.sherlochao.dao;


import com.sherlochao.model.SMSValidCode;

public interface SMSValidCodeDao extends GenericDao<SMSValidCode>{

	int delete1(String smsValidcodeId);
	
	int insert(SMSValidCode record);

	SMSValidCode findSMS(SMSValidCode record);

	int updateById(SMSValidCode record);
	
	void updateBySMSId(SMSValidCode record);

}
