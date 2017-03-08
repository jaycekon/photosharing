package com.sherlochao.dao;

import java.util.List;

import com.sherlochao.model.User;

public interface UserDao extends GenericDao<User> {
	List<User> getAllOnLineUser();
	
	User checkUser(User user);
	
	 /**
     * 查找用户名或者手机是否已经被注册
     *
     * @param
     * @return
     */
    List<User> findUserByEmail(User user);
}
