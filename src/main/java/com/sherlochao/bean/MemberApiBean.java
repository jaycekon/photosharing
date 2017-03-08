package com.sherlochao.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.ToString;

/**
 * 
 * 
 */
@Data
@ToString
public class MemberApiBean implements Serializable{

	private static final long serialVersionUID = 1L;

    private Integer memberId;
    
    private String memberName;//会员名称
    
    private String memberAvatar;//会员头像
    
    private Integer memberSex;//会员性别
    
    private Long memberBirthday;//会员生日

    private String memberAreainfo;//地区内容
        
	private String memberIntroduction; //签名档
	
	private String memberNickname; //昵称
	
	/**
	 * 登录标示
	 */
	private String sessionId;
	
	/**
	 * 用户名code
	 */
	private String memberNameCode;

	private Integer memberRole;
	
}