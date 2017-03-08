package com.sherlochao.dao.impl;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.sherlochao.dao.SMSValidCodeDao;
import com.sherlochao.model.SMSValidCode;

@Repository("SMSValidCodeDao")
public class SMSValidCodeDaoImpl extends GenericDaoImpl<SMSValidCode> implements SMSValidCodeDao{
	
	@Resource
	private SessionFactory sessionFactory;
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}


	@Override
	public int insert(SMSValidCode record) {
		this.getSession().save(record);
		return 0;
	}

	@Override
	public SMSValidCode findSMS(SMSValidCode record) {
		String hql = "from SMSValidCode where validcode = ? and mobile = ? and codeType = ?";
		Query query = this.getSession().createQuery(hql);
		query.setString(0, record.getValidcode());
		query.setString(1, record.getMobile());
		query.setInteger(2, record.getCodeType());
		return (SMSValidCode) query.uniqueResult();
	}

	@Override
	public int updateById(SMSValidCode record) {
		this.getSession().saveOrUpdate(record);
		return 1; //有问题，不应该这样返回
	}
	
	@Override
	public void updateBySMSId(SMSValidCode record) {
		this.getSession().saveOrUpdate(record);
	}


	@Override
	public int delete1(String smsValidcodeId) {
		SMSValidCode entity = this.load(smsValidcodeId);
	    this.getSession().delete(entity);
	    return 0;
	}

}
