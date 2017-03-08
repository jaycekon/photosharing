package com.sherlochao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "SMSValidCode")
public class SMSValidCode {
	
    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "smsValidcode_id") 
	private Integer smsValidcodeId;   //id

	@Column(name = "validcode")
    private String validcode;   //验证码

	@Column(name = "mobile")
	private String mobile; //手机号码

	@Column(name = "createtime")
    private Long createtime;  //创建时间

	@Column(name = "checktime")
    private Long checktime;   //验证时间

	@Column(name = "userAgent")
    private String userAgent;  //请求验证码的用户标示

	@Column(name = "ip")
    private String ip;   //请求来源

	@Column(name = "codeType")
    private Integer codeType;  //短信验证码的类型，1:第三方登录绑定手机验证码 2:注册时短信验证码 3:找回密码
}
