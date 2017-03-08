package com.sherlochao.util;

import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;

import com.sherlochao.model.Member;
import com.sherlochao.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description:  第三方登录部分公用逻辑
 */
public class ThirdLoginUtil {

    
	/**
     * 手动让用户登录
     *
     * @param member
     * @param request
     * @param response
     */
    public static void letLogin(Member member, HttpServletRequest request, HttpServletResponse response) {
        PrincipalCollection principals = new SimplePrincipalCollection(
                member.getMemberName(), "MobileRealm");
        WebSubject.Builder builder = new WebSubject.Builder(
                request,
                response);
        builder.principals(principals);
        builder.authenticated(true);
        WebSubject subject = builder.buildWebSubject();
        ThreadContext.bind(subject);
    }
}
