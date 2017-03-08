package com.sherlochao.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 类名称：Member
 * 类描述：会员实体类
 */
@Data
@ToString
@Entity
@Table(name = "member")
public class Member implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2906447779118672379L;
    
    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Integer memberId;
    
    @Column(name = "member_name")
    private String memberName;//会员名称
    
    @Column(name = "member_avatar")
    private String memberAvatar;//会员头像
    
    @Column(name = "member_sex")
    private Integer memberSex;//会员性别    1男 2女
    
    @Column(name = "member_birthday")
    private Long memberBirthday;//会员生日
    
    @Column(name = "member_passwd")
    private String memberPasswd;//密码
    
    @Column(name = "member_email")
    private String memberEmail;//邮箱

    @Column(name = "member_area_info")
    private String memberAreainfo;//地区内容    所在地
    
    @Column(name = "member_mobile")
    private String memberMobile = ""; //手机号
        
	@Column(name = "member_introduction")
	private String memberIntroduction; //签名档  个性签名

	@Column(name = "member_nickname")
	private String memberNickname; //昵称

    @Column(name = "member_realname")
    private String memberRealname; //真实姓名

    private Integer memberRole; //会员角色 1访客 2注册用户 3管理员

    @Column(name = "member_createtime")
    private String memberCreatetime; //创建时间-数据库字段

    @Column(name = "member_login_num")
    private Integer memberLoginNum;//登录次数

    @Column(name = "member_login_time")
    private Long memberLoginTime;//当前登录时间

    @Column(name = "member_old_login_time")
    private Long memberOldLoginTime;//上次登录时间

    @Column(name = "member_login_ip")
    private String memberLoginIp;//当前登录ip

    @Column(name = "member_old_login_ip")
    private String memberOldLoginIp;//当前登录ip

    @Column(name = "shared_adminstrator_state")
    private Integer administratorState; //1管理员允许 2管理员禁止
    
}