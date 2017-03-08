package com.sherlochao.common;

import com.sherlochao.model.Member;
import com.sherlochao.service.MemberService;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author
 * @Package 
 * @Description:
 * @date
 */
public class CacheUtils {

    /**
     * 获取SessionUser
     *
     * @return
     */
    public static CacheUser getCacheUser() {

        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        if (null != session) {
            //仅用于测试
            CacheUser cacheUser;//= initCacheUser("front")
            Subject currentUser = SecurityUtils.getSubject();
            cacheUser = (CacheUser) session.getAttribute(Constants.USER_SESSION_KEY);
            if (cacheUser == null) {
                cacheUser = CacheUtils.initCacheUser(currentUser.getPrincipal().toString());
            }
            return cacheUser;
        } else {

            return null;
        }
    }

    /**
     * 初始化CacheUser对象
     *
     * @param memberName
     * @return
     */
    public static CacheUser initCacheUser(String memberName) {

        MemberService memberService = SpringContextUtil.getBean(MemberService.class);

        Member validmember = new Member();
        validmember.setMemberMobile(memberName);
        validmember.setMemberName(memberName);
        Member member=memberService.findMemberByNameOrMobile(validmember);

        CacheUser cacheUser = new CacheUser();
        cacheUser.setMember(member);
        
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        if (null != session) {
            session.setAttribute(Constants.USER_SESSION_KEY, cacheUser);	
        } else {
            throw new RuntimeException("CacheUser初始化失败");
        }
        return cacheUser;
    }
    
    /**
     * 判断用户是否登录
     * @return
     */
    public static boolean isLogin(){
    	Subject currentUser = SecurityUtils.getSubject();
    	return currentUser.isAuthenticated();
    }
}
