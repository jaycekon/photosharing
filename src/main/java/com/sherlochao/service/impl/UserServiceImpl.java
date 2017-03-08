package com.sherlochao.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sherlochao.dao.UserDao;
import com.sherlochao.model.User;
import com.sherlochao.service.UserService;


@Transactional
@Service("userService")
public class UserServiceImpl implements UserService{
	
	@Resource
	private UserDao userDao;

	@Override
	public String saveUser(User user) {
	    return userDao.save(user);
	}

	@Override
	public void updateUser(User user) {
		userDao.saveOrUpdate(user);
	}

	@Override
	public void deleteUser(String id) {
		userDao.delete(id);
	}

	@Override
	public User findUserById(String id) {
		return userDao.get(id);
	}

	@Override
	public User checkUser(User user) {
		User user1 = userDao.checkUser(user);
		if(user1!=null){
			user1.setStatus("1");
			this.userDao.saveOrUpdate(user1);
			return user1;
		}
		return null;
	}

	@Override
	public void loginout(User user) {
		if(user!=null){
			user.setStatus("0");
			this.userDao.saveOrUpdate(user);
		}
	}

	@Override
	public List<User> getAllOnLineUser() {
		return this.userDao.getAllOnLineUser();
	}

	@Override
	public User findUserByEmail(User user) {
		List<User> qureyMembers = userDao.findUserByEmail(user);  //先判断用户名或者密码是否可用
        if (qureyMembers.size() != 0) {
            return qureyMembers.get(0);
        }
        return null;
	}
	
	/**
     * 仅仅修改当前登陆人的
     *
     * @param memberMobile
     * @return
     */
    @Override
    public void updateweiUser(String userId) {
//        List<User> menbers = userDao.findById(userId);
//        User member = null;
//        if (menbers != null && menbers.size() != 0) {
//            member = menbers.get(0);
//        }
//
//        User member9 = new User();
//        if (member != null) {
//            member9.setUserId(member.getUserId());
//            member9.setMemberOldLoginTime(member.getMemberLoginTime());// 上次登陆时间
//            member9.setMemberLoginTime(System.currentTimeMillis());// 最后登陆时间
//            member9.setMemberLoginNum(1);// 登陆次数
//            memberDao.updateMember(member9);
//        }
    }

}
