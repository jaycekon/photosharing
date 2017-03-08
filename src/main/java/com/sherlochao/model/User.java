package com.sherlochao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.hibernate.annotations.GenericGenerator;


@Data
@Entity
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy = "uuid")
	@Column(name = "user_id") 
	private String userId;//id
	
	@Column(name = "username")
	private String username; //昵称
	
	@Column(name = "password")
	private String password; //密码
	
	@Column(name = "createDate")
	private String createDate; //创建时间
	
	@Column(name = "sex")
	private String sex; //性别
	
	@Column(name = "status")
	private String status; //是否激活
	
	@Column(name = "avatar")
	private String avatar; //头像
	
	@Column(name = "introduction")
	private String introduction; //签名档

	@Column(name = "user_mobile")
	private String userMobile; //手机号
	
	@Column(name = "login_status")
	private Integer loginStatus; //用户登录状态
	
	@Column(name = "email")
	private String email; //手机号
	
	//private Integer memberLoginNum;//登录次数
	
	
	
}
