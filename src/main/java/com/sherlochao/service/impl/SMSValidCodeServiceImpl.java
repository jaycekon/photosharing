package com.sherlochao.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sherlochao.dao.SMSValidCodeDao;
import com.sherlochao.model.SMSValidCode;
import com.sherlochao.service.SMSValidCodeService;

@Transactional
@Service("SMSValidCodeService")
public class SMSValidCodeServiceImpl implements SMSValidCodeService{

	
	@Resource
	private SMSValidCodeDao smsDao;
	
	@Override
	public int delete(String smsValidcodeId) {
		return smsDao.delete1(smsValidcodeId);
	}

	@Override
	public int save(SMSValidCode record) {
		return smsDao.insert(record);
	}

	@Override
	public SMSValidCode findSMS(SMSValidCode record) {
		return smsDao.findSMS(record);
	}

	@Override
	public int updateById(SMSValidCode record) {
		return smsDao.updateById(record);
	}
	
	@Override
	public void updateBySMSId(SMSValidCode record) {
		smsDao.updateBySMSId(record);
	}
	

}
