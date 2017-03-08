package com.sherlochao.dao.impl;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


import com.sherlochao.dao.GenericDao;
import com.sherlochao.util.GenericClass;

public class GenericDaoImpl<T> implements GenericDao<T>{

	private Class clazz=GenericClass.getGenericClass(this.getClass());
	
	@Resource
	private SessionFactory sessionFactory;
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
		//return sessionFactory.openSession();
	}

	@Override
	public T get(String id) {
		// TODO Auto-generated method stub
		return (T)this.getSession().get(clazz, id);
	}

	@Override
	public String save(T entity) {
		// TODO Auto-generated method stub
		return (String)this.getSession().save(entity);
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		T entity = this.load(id);
		this.getSession().delete(entity);
	}

	@Override
	public T load(String id) {
		// TODO Auto-generated method stub
		return (T)this.getSession().load(clazz, id);
	}

	@Override
	public List<T> findAll() {
		// TODO Auto-generated method stub
		String hql="from "+clazz.getName();
		List<T> ts = this.getSession().createQuery(hql).list();
		return ts;
	}

	@Override
	public void persist(T entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveOrUpdate(T entity) {
		// TODO Auto-generated method stub
		this.getSession().saveOrUpdate(entity);
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub		
	}

	@Override
	public List<T> findObjectsByConditionWithNoPage(String whereHql,
			Object[] params, LinkedHashMap<String, String> orderBy) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
