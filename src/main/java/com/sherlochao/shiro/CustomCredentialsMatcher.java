package com.sherlochao.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

import com.sherlochao.util.Digests;


/**
 * 自定义shiro验证时使用的加密算法
 */
public class CustomCredentialsMatcher extends HashedCredentialsMatcher{

    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        Object accountCredentials = getCredentials(info); // 数据库密码
        boolean isMobileLogin = token.isMobileLogin();    // 是否手机登录 
        if(isMobileLogin){ // 微信自动登录
        	return true;
        }
        //lihaishan 2015-12-02 将注册、登录、修改密码等原来使用的SHA1加密改为MD5加密 ，这里主要要考虑兼容移植后的来自旧系统的用户
//        //将密码加密与系统加密后的密码校验，内容一致就返回true,不一致就返回false
//        return Digests.validatePassword(String.valueOf(token.getPassword()), accountCredentials + "");
        return Digests.validatePasswordWithMD5AndSalt(String.valueOf(token.getPassword()), accountCredentials + "");
    }

}
