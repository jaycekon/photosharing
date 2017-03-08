package com.sherlochao.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.sherlochao.dao.UserDao;
import com.sherlochao.model.User;


@Repository("userDao")
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao{

	@Resource
	private SessionFactory sessionFactory;
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
		//return sessionFactory.openSession();
	}
	
	@Override
	public List<User> getAllOnLineUser() {
		// TODO Auto-generated method stub
		String hql = "from User where status = ?";
		Query query = this.getSession().createQuery(hql);
		query.setString(0, "1");
		return query.list();
	}

	@Override
	public User checkUser(User user) {
		// TODO Auto-generated method stub
		String hql = "from User where username = ? and password = ?";
		Query query = this.getSession().createQuery(hql);
		query.setString(0, user.getUsername());
		query.setString(1, user.getPassword());
		return (User) query.uniqueResult();
	}

	@Override
	public List<User> findUserByEmail(User user) {
		// TODO Auto-generated method stub
		String hql = "from User where email = ?";
		Query query = this.getSession().createQuery(hql);
		query.setString(0, user.getEmail());
		return query.list();
	}

}
