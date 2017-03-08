package com.sherlochao.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sherlochao.bean.MemberApiBean;
import com.sherlochao.bean.UserApiBean;
import com.sherlochao.model.Member;
import com.sherlochao.model.User;
import com.sherlochao.service.MemberService;
import com.sherlochao.service.UserService;
import com.sherlochao.util.Digests;
import com.sherlochao.util.JsonUtils;
import com.sherlochao.util.MyBeanUtils;
import com.sherlochao.util.PinYinUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.sherlochao.util.ThirdLoginUtil.letLogin;

@Controller
@RequestMapping("/loginapi")
public class LoginApi {
	
	@Resource
	private MemberService memberService;
	
	/**
     * 登录api
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public JSONObject loginIn(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password, HttpSession session,HttpServletRequest request,
            HttpServletResponse response) {
        JSONObject jsonObj = new JSONObject();
        try {
            Member member = new Member();
            member.setMemberMobile(username);
            member.setMemberName(username);
            Member m = memberService.findMemberByNameOrMobile(member);
            if (null == m) {
                jsonObj.put("result", 0);
                jsonObj.put("msg", "用户名错误");
            } else {
                boolean isValidate = Digests.validatePasswordWithMD5AndSalt(password, m.getMemberPasswd());
                if (isValidate) {
                    letLogin(m, request, response);  //登录一下
                    MemberApiBean bean = new MemberApiBean();
                    bean.setSessionId(session.getId());
                    MyBeanUtils.copyBeanNotNull2Bean(m, bean);
                    bean.setMemberNameCode(PinYinUtil.getPingYin(bean.getMemberName()));
                    //memberService.updateweiMember(m.getMemberId());//修改登陆者的登陆次数，登陆时间 ******暂时不做这里
                    jsonObj.put("result", 1);
                    jsonObj.put("msg", "成功登录");
                    jsonObj.put("data", JSONArray.fromObject(bean, JsonUtils.getJsonConfig()));                 
                } else {
                    jsonObj.put("result", 0);
                    jsonObj.put("msg", "密码错误");
                }
            }
        } catch (UnknownAccountException | IncorrectCredentialsException e) {
            jsonObj.put("result", 0);
            jsonObj.put("msg", "用户名/密码错误");
        } catch (Exception e) {
            e.printStackTrace();
            jsonObj.put("result", 0);
            jsonObj.put("msg", "服务器导常");
        }

        return jsonObj;
    }

    /**
     * 用户登出操作
     *
     * @return
     */
    @RequestMapping("/loginout")
    @ResponseBody
    public JSONObject loginOut() {
        JSONObject jsonObj = new JSONObject();
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            jsonObj.put("result", 1);
            jsonObj.put("msg", "成功登出");
        } catch (Exception e) {
            jsonObj.put("result", 0);
            jsonObj.put("msg", "服务器导常");
        }

        return jsonObj;
    }
}
