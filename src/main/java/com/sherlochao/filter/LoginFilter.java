package com.sherlochao.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sherlochao.model.User;
import com.sherlochao.util.SessionUtils;

public class LoginFilter implements Filter {
	private List list;

	public void init(FilterConfig config) throws ServletException {
		list = new ArrayList();
		list.add("/user/toLogin/");
		list.add("/user/toRegister/");
		list.add("/user/login/");
		list.add("/user/register/");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String path = req.getServletPath();
		System.out.println(path);
		if (list != null && list.contains(path)) {
			chain.doFilter(req, res);
			return;
		}
		User user = SessionUtils.getSysUserFromSession(req);
		if (user != null) {
			chain.doFilter(req, res);
		} else {
			res.sendRedirect(req.getContextPath());
		}
	}

	public void destroy() {

	}

}
