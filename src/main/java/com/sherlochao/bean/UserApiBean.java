package com.sherlochao.bean;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 客户端接口所用的实体－会员
 * @author KVIUFF
 *
 */
@Data
@ToString
public class UserApiBean implements Serializable {

	private static final long serialVersionUID = -4637080967621999359L;
	
	/**
	 * 会员id
	 */
	private String userId;
	
	/**
	 * 会员名称
	 */
	private String userame;
	
	
	/**
	 * 会员头像
	 */
	private String avatar;
	
	/**
	 * 会员性别
	 */
	private String sex;
	
	
	/**
	 * 邮箱
	 */
	private String email;
	
	
	/**
	 * 手机号
	 */
	private String userMobile;
	
	
	/**
	 * 用户名code
	 */
	private String userNameCode;
	
	
	/**
	 * 地区内容
	 */
	private String memberAreainfo;


	/**
	 * 登录标示
	 */
	private String sessionId;

}
