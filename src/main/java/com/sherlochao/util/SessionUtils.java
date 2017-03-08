package com.sherlochao.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sherlochao.model.User;

public class SessionUtils {

	public static User getSysUserFromSession(HttpServletRequest request){
		HttpSession session=request.getSession();
		if(session==null){
			return null;
		}
		User user=(User) session.getAttribute("user");
		return user;
	}
}
