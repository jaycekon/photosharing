package com.sherlochao.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Sherlock_chao on 2016/11/16.
 */

@Data
@ToString
@Entity
@Table(name = "shared")
public class Shared implements Serializable {


    private static final long serialVersionUID = 7656468478300181982L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shared_id")
    private Integer sharedId;

    @Column(name = "member_id")
    private Integer memberId;

    @Column(name = "shared_content")
    private String sharedContent;

    @Column(name = "sharedView")
    private Integer sharedView; //浏览量

    @Column(name = "shared_createtime")
    private String sharedCreatetime; //发布时间

    @Column(name = "shared_photo")
    private String sharedPhoto;

    @Column(name = "shared_state")
    private Integer sharedState; //分享的状态 1删除 2屏蔽 3公开

    @Column(name = "shared_adminstrator_state")
    private Integer administratorState; //1管理员允许 2管理员禁止

    @Column(name = "shared_create_time")
    private Date sharedCreateTime; //发布时间



}
